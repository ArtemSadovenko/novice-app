import axios from 'axios';
import { LoginRequest, RegisterRequest, UserData } from "../types/auth";
import { LoginResponse } from "../types/auth";
import { CreateRoomDetailsRequest, CreateRoundResultsRequest, RoomDetails, RoomPreview, RoundResults } from '../types/roomdetails';

const BASE_URL = "http://localhost:8500/api/v1/room-details"
const token = localStorage.getItem('token')

export const createRoom = async (data: CreateRoomDetailsRequest): Promise<RoomDetails> => {
   
    try {
        const response = await axios.post(`${BASE_URL}`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Create room failed:', error);
        throw new Error('Create room failed');
    }
}


export const updateRoom = async (data: CreateRoomDetailsRequest): Promise<RoomDetails> => {
    
    try {
        const response = await axios.put(`${BASE_URL}`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Update room failed:', error);
        throw new Error('Update room failed');
    }
}


export const deleteRoom = async (id: string): Promise<boolean> => {
    
    try {
        const response = await axios.delete(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('delete room failed:', error);
        throw new Error('delete room failed');
    }
}

export const setRoomResults = async (id: string, data: CreateRoundResultsRequest[]): Promise<RoomDetails> => {
    
    try {
        const response = await axios.post(`${BASE_URL}/${id}`, data, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('set room results failed:', error);
        throw new Error('set room results  failed');
    }
}

export const getRoomDetailsById = async (id: string): Promise<RoomDetails> => {
    
    try {
        const response = await axios.get(`${BASE_URL}/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get room failed:', error);
        throw new Error('Get room failed');
    }
}

export const getAllRoomDetails = async (): Promise<RoomDetails[]> => {
    
    try {
        const response = await axios.get(`${BASE_URL}`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get rooms failed:', error);
        throw new Error('Get rooms failed');
    }
}

export const getJudgeAllRoomDetails = async (): Promise<RoomDetails[]> => {
    
    try {
        const response = await axios.get(`${BASE_URL}/judge-rooms`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get rooms failed:', error);
        throw new Error('Get rooms failed');
    }
}

export const getJudgeAllRoomPreview = async (): Promise<RoomPreview[]> => {
    
    try {
        const response = await axios.get(`${BASE_URL}/judge-preview`, {
            headers: {
                "Authorization": `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Get rooms failed:', error);
        throw new Error('Get rooms failed');
    }
}