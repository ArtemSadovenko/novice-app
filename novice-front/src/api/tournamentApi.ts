import axios from 'axios';
import { LoginRequest, RegisterRequest, UserData } from "../types/auth";
import { LoginResponse } from "../types/auth";
import { CreateTournamentRequest, Tournament } from '../types/tournament';
import { RoundTable } from '../types/room';


const BASE_URL = "http://localhost:8500/api/v1/tournament"
const token = localStorage.getItem('token')

export const createTournament = async (data: CreateTournamentRequest): Promise<Tournament> => {

    try {
        const response = await axios.post(`${BASE_URL}`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Create Tournament failed:', error);
        throw new Error('Create Tournament failed');
    }
}


export const updateTournament = async (data: CreateTournamentRequest): Promise<Tournament> => {

    try {
        const response = await axios.put(`${BASE_URL}`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Update Tournament failed:', error);
        throw new Error('Update Tournament failed');
    }
}


export const deleteTournament = async (id: string): Promise<boolean> => {

    try {
        const response = await axios.delete(`${BASE_URL}${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('delete Tournament failed:', error);
        throw new Error('delete Tournament failed');
    }
}

export const getTournamentById = async (id: string): Promise<Tournament> => {

    try {
        const response = await axios.get(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get Tournament failed:', error);
        throw new Error('Get Tournament failed');
    }
}

export const getAllTournament = async (): Promise<Tournament[]> => {

    try {
        const response = await axios.get(`${BASE_URL}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get Tournaments failed:', error);
        throw new Error('Get Tournaments failed');
    }
}

export const genarateRooms = async (id: string): Promise<RoundTable> => {
    try {
        const response = await axios.post(`${BASE_URL}/${id}/generate-rooms`, {}, {

            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Generate Table failed:', error);
        throw new Error('Generate Table failed');
    }
}

export const getRoundTable = async (id: string, roundNumber: number): Promise<RoundTable> => {
    try {
        const response = await axios.get(`${BASE_URL}/${id}/${roundNumber}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get Table failed:', error);
        throw new Error('Get Table failed');
    }
}