package com.novice.debatenovice.dto.table;

import com.novice.debatenovice.dto.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class RoundTableDTO {
    private Long tournamentId;
    private Integer roundNumber;
    private List<RoomDTO> rooms;
}
