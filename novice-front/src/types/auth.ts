export interface UserData {
  id: string;
  email: string;
  username: string;
  roles: USER_ROLE[];
  experience: USER_EXPERIENCE;
  averageScore: string;
}

export enum USER_EXPERIENCE {
  NOVICE = "NOVICE",
  EXPERIENCED = "EXPERIENCED",
  OLD = "OLD"
}



export enum USER_ROLE {
  ADMIN,
  USER,
  GUEST
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  userId: string;
  token: string;
}


export interface AuthTokenPayload extends UserData {
  exp: number;
  iat: number;
  userId: string;
  sub: string;
}

export interface AuthContextType {
  currentUser: UserData | null;
  id: string | null;
  token: string | null;
  loading: boolean;
  error: string | null;
  login: (email: string, password: string) => Promise<UserData>;
  register: (data: RegisterRequest) => Promise<boolean>;
  logout: () => void;
  getAuthHeaders: () => Record<string, string>;
  checkAuthStatus: () => void;
}

export interface RegisterRequest {
  email: string;
  password: string;
  username: string
  experience: USER_EXPERIENCE;
  roles: USER_ROLE[]
}

export interface CreateUserRequest{
  email: string;
  password: string;
  username: string
  experience: USER_EXPERIENCE;
  roles: USER_ROLE[]
}

export interface UpdateUserRequest{
  id: number;
  email: string;
  password: string;
  username: string
  experience: USER_EXPERIENCE;
  roles: USER_ROLE[]
}

export interface UserPreview{
  id: string;
  email: string;
  username: string;
  experience: USER_EXPERIENCE;
  averageScore: string;
}