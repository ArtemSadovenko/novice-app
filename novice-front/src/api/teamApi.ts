import axios from 'axios';
import { LoginRequest, RegisterRequest, UserData } from "../types/auth";
import { LoginResponse } from "../types/auth";
import { CreateTeamRequest, RegisterTeamRequest, Team } from '../types/team';

const BASE_URL = "http://localhost:8500/api/v1/team"
const token = localStorage.getItem('token')

export const createTeam = async (data: CreateTeamRequest): Promise<Team> => {
   
    try {
        const response = await axios.post(`${BASE_URL}/`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Create Team failed:', error);
        throw new Error('Create Team failed');
    }
}


export const updateTeam = async (data: CreateTeamRequest): Promise<Team> => {
    
    try {
        const response = await axios.put(`${BASE_URL}/`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Update Team failed:', error);
        throw new Error('Update Team failed');
    }
}


export const deleteTeam = async (id: string): Promise<boolean> => {
    
    try {
        const response = await axios.delete(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('delete Team failed:', error);
        throw new Error('delete Team failed');
    }
}

export const getTeamById = async (id: string): Promise<Team> => {
    
    try {
        const response = await axios.get(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get Team failed:', error);
        throw new Error('Get Team failed');
    }
}

export const getAllTeam = async (): Promise<Team[]> => {
    
    try {
        const response = await axios.get(`${BASE_URL}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get Teams failed:', error);
        throw new Error('Get Teams failed');
    }
}

export const registerTeam = async (data: RegisterTeamRequest): Promise<Team> =>{
    try {
        const response = await axios.post(`${BASE_URL}/regiter`,data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('register Teams failed:', error);
        throw new Error('register Teams failed');
    }
}

