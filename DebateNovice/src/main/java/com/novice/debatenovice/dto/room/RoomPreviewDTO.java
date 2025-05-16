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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomPreviewDTO {
    private Long id;
    private Integer roundNumber;
    private String roomName;
    private Long tournamentId;
    private String tournamentName;
}
