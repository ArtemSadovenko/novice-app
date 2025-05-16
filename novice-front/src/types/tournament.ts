import { UserData } from "./auth"
import { RoomDetails } from "./roomdetails"
import { Team } from "./team"

export interface Tournament {
    id: string
    name: string
    topics: Record<string, string>
    teams: Team[] 
    judges: UserData[] 
    roomDetails: RoomDetails[] | []
    tournamentStatus: TOURNAMENT_STATUS
}

export enum TOURNAMENT_STATUS{
    NEW = "NEW",
    OPEN = "OPEN",
    IN_PROGRESS = "IN_PROGRESS",
    CLOSED = "CLOSED",
}

export interface CreateTournamentRequest{
    name: string
    topics: Record<string, string>
    judges: UserData[]
}

export interface UpdateTournamentRequest{
    id: string
    name: string
    topics: Record<string, string>
}