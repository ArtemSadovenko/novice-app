package com.novice.debatenovice.dto.team;

import com.novice.debatenovice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private BigDecimal teamPoints;
    private String name;
    private List<UserDTO> teamMembers;
    private Long tournamentId;
}
