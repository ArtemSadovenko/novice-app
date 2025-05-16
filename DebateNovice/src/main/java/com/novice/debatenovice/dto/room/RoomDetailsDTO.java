package com.novice.debatenovice.dto.room;

import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.enums.ROUND_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailsDTO {
    private Long id;
    private Integer roundNumber;
    private String roomName;
    private Long tournamentId;

    //TODO add feedback

    private ROUND_STATUS roundStatus;

    private List<UserDTO> judges;

    private List<RoomPositionDTO> positions;

    private List<RoundResultsDTO> results;
}
