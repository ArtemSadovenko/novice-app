import axios from 'axios';
import { CreateUserRequest, LoginRequest, RegisterRequest, UpdateUserRequest, UserData } from "../types/auth";
import { LoginResponse } from "../types/auth";

const BASE_URL = "http://localhost:8500/api/v1/user"
const token = localStorage.getItem('token')

export const getUserById = async (id: string): Promise<UserData> => {

    try {
        const response = await axios.get<UserData>(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        return response.data;

    } catch (error) {
        console.error('User fetch fail: ', error);
        throw new Error('User fetch fail');
    }
};



export const getAllUsers = async (): Promise<UserData[]> => {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.get<UserData[]>(`${BASE_URL}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        return response.data;

    } catch (error) {
        console.error('User fetch fail: ', error);
        throw new Error('User fetch fail');
    }
};


export const createUser = async (data: CreateUserRequest): Promise<UserData> => {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.post<UserData>(`${BASE_URL}`,
            data,
            {
                headers: {
                    "Authorization": `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

        return response.data;

    } catch (error) {
        console.error('User create fail: ', error);
        throw new Error('User create fail');
    }
};


export const deleteUserById = async (id: string):Promise<boolean> => {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.get<UserData>(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        return true;

    } catch (error) {
        console.error('User fetch fail: ', error);
        throw new Error('User fetch fail');
    }
};


export const updateUser = async (data: UpdateUserRequest): Promise<UserData> => {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.put<UserData>(`${BASE_URL}`,
            data,
            {
                headers: {
                    "Authorization": `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

        return response.data;

    } catch (error) {
        console.error('User create fail: ', error);
        throw new Error('User create fail');
    }
};

export const getAllUsersPreview = async (): Promise<UserData[]> => {
    const token = localStorage.getItem('token')

    try {
        const response = await axios.get<UserData[]>(`${BASE_URL}/all-preview`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        return response.data;

    } catch (error) {
        console.error('User fetch fail: ', error);
        throw new Error('User fetch fail');
    }
};
