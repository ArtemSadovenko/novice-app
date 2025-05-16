package com.novice.debatenovice.dto.room;

import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.enums.POSITION;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDTO {
    private Long roomDetailsId;
    private String roomName;
    private Map<POSITION, TeamDTO> teams;
    private List<UserDTO> judges;
}
