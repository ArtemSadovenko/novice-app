
import { createContext, useState, useEffect, useContext, ReactNode, JSX } from 'react';
import { jwtDecode } from 'jwt-decode';
import { AuthContextType, AuthTokenPayload, UserData, LoginResponse, RegisterRequest } from '../types/auth';
import { login, register } from '../api/authapi';
import { getUserById } from "../api/userApi"
const AuthContext = createContext<AuthContextType | null>(null);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps): JSX.Element => {
  const [currentUser, setCurrentUser] = useState<UserData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [id, setId] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);


  useEffect(() => {
    checkAuthStatus();
  }, []);

  const checkAuthStatus = async (): Promise<void> => {
    try {
      const storedToken = localStorage.getItem('token');

      if (!storedToken) {
        setCurrentUser(null);
        setLoading(false);
        return;
      }

      // Verify token hasn't expired
      const decodedToken = jwtDecode<AuthTokenPayload>(storedToken);
      const currentTime = Date.now() / 1000;

      if (decodedToken.exp < currentTime) {
        // Token expired
        logout();
      } else {
        // Set token and id
        setToken(storedToken);
        setId(decodedToken.userId);

        // Fetch user data
        try {
          const userData = await getUserById(decodedToken.userId);
          setCurrentUser(userData);
        } catch (err) {
          console.error("Error fetching user data:", err);
          logout();
        }
      }
    } catch (err) {
      console.error("Token validation error:", err);
      logout();
    } finally {
      setLoading(false);
    }
  };


  const loginAuth = async (email: string, password: string): Promise<UserData> => {
    try {
      setLoading(true);
      setError(null);
      const data: LoginResponse = await login({ email, password });
      setToken(data.token);
      localStorage.setItem('token', data.token);
      const decodedToken = jwtDecode<AuthTokenPayload>(data.token);

      setId(decodedToken.id);

      const userData = await getUserById(decodedToken.userId)
      let user: UserData = userData;
      setCurrentUser(user);
      return userData;
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Authentication failed';
      setError(errorMessage);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const logout = (): void => {
    localStorage.removeItem('token');
    setCurrentUser(null);
  };

  const registerAuth = async (data: RegisterRequest): Promise<boolean> => {
    try {
      setLoading(true);
      setError(null);
      return await register(data);

    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Registration failed';
      setError(errorMessage);
      throw err;
    } finally {
      setLoading(false);
    }
  }

  const getAuthHeaders = (): Record<string, string> => {
    const token = localStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
  };

  const value: AuthContextType = {
    currentUser,
    loading,
    error,
    id,
    token,
    login: loginAuth,
    register: registerAuth,
    logout,
    getAuthHeaders,
    checkAuthStatus,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
