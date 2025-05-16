import { UserData } from "./auth"
import { Team } from "./team"

export interface RoomDetails {
    id: string
    roundNumber: string
    roomName: string
    tournamentId: string
    roundStatus: ROUND_STATUS
    judges: UserData[]
    positions: RoomPosition[]
    results: RoundResults[]
}

export enum ROUND_STATUS {
    IN_PROGRESS,
    COMPLETED
}

export interface RoomPosition {
    id: string
    position: POSITION
    team: Team
    roomDetailsId: string
}

export enum POSITION {
    OPENING_PROPOSITION = "OPENING_PROPOSITION",
    OPENING_OPPOSITION = "OPENING_OPPOSITION",
    CLOSING_PROPOSITION = "CLOSING_PROPOSITION",
    CLOSING_OPPOSITION = "CLOSING_OPPOSITION",
}

export interface RoundResults {
    id: string
    place: PLACE
    speakerPoints: Record<string, string>
    roomDetailsId: string
    team: Team
}

export interface CreateRoundResultsRequest {
    place: PLACE
    speakerPoints: Record<string, string>
    roomDetailsId: string
    team: Team
}


export enum PLACE {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
}

export interface CreateRoomDetailsRequest {
    id?: string
    roundNumber?: string
    roomName?: string
    tournamentId?: string
    roundStatus?: ROUND_STATUS
    judges?: UserData[]
    positions?: RoomPosition[]
    results?: RoundResults[]
}


export interface CreateRoomDetailsResponse {
    id?: string
    roundNumber?: string
    roomName?: string
    tournamentId?: string
    roundStatus?: ROUND_STATUS
    judges?: UserData[]
    positions?: RoomPosition[]
    results?: RoundResults[]
}

export interface RoomPreview {
    id: string
    roundNumber: string
    roomName: string
    tournamentId: string
    tournamentName: string
}