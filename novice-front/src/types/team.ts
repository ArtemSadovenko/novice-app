import { UserData } from "./auth"

export interface Team {
    id: string
    teamPoints: string
    name: string
    teamMembers: UserData[]
    tournamentId: string
}

export interface RegisterTeamRequest {
    name: string
    teamMembersIds: string[]
    tournamentId: string
}

export interface CreateTeamRequest {
    name: string
    teamMembers: UserData[]
    tournamentId: string
}

export interface UpdateTeamRequest {
    id: string;
    name: string
    teamMembers: UserData[]
    tournamentId: string
}