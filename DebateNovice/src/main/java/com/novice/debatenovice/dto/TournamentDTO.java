package com.novice.debatenovice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.enums.TOURNAMENT_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("topics")
    private Map<String, String> topics;

    @JsonProperty("teams")
    private List<TeamDTO> teams;

    @JsonProperty("judges")
    private List<UserDTO> judges;

    @JsonProperty("roomDetails")
    private List<RoomDetailsDTO> roomDetails;

    @JsonProperty("tournamentStatus")
    private TOURNAMENT_STATUS tournamentStatus;

    @JsonCreator
    public static TournamentDTO create(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("topics") Map<String, String> topics,
            @JsonProperty("teams") List<TeamDTO> teams,
            @JsonProperty("judges") List<UserDTO> judges,
            @JsonProperty("roomDetails") List<RoomDetailsDTO> roomDetails,
            @JsonProperty("tournamentStatus") TOURNAMENT_STATUS tournamentStatus
    ) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setTopics(topics);
        dto.setTeams(teams == null ? new ArrayList<>() : teams);
        dto.setJudges(judges == null ? new ArrayList<>() : judges);
        dto.setRoomDetails(roomDetails);
        dto.setTournamentStatus(tournamentStatus);
        return dto;
    }

}
