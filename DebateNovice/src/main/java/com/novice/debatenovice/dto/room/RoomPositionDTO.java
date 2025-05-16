package com.novice.debatenovice.dto.room;

import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.enums.POSITION;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomPositionDTO {
    private Long id;
    private POSITION position;
    private TeamDTO team;
    private Long roomDetailsId;
}
