import axios from 'axios';
import { LoginRequest, RegisterRequest, UserData } from "../types/auth";
import { LoginResponse } from "../types/auth";

const BASE_URL = "http://localhost:8500/api/v1"

//Login api
export const login = async (data: LoginRequest): Promise<LoginResponse> => {
  try {
    const response = await axios.post<LoginResponse>(
      `${BASE_URL}/auth/login`,
      data, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response.data;
  } catch (error) {
    console.error('Login failed:', error);
    throw new Error('Login failed');
  }
};

export const register = async (data: RegisterRequest): Promise<boolean> => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/register`, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return true;
  } catch (error) {
    console.error('Register failed:', error);
    throw new Error('Register failed');
  }
};



