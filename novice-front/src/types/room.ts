import { UserData } from "./auth"
import { POSITION } from "./roomdetails"
import { Team } from "./team"

export interface RoundTable {
    tournamentId: string
    roundNumber: string
    rooms: Room[]
}

export interface Room {
    roomDetailsId: string
    roomName: string
    teams: Record<POSITION, Team>
    judges: UserData[]
}