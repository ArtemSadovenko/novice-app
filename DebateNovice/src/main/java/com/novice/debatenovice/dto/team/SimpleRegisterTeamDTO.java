package com.novice.debatenovice.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRegisterTeamDTO {
    private Long id;
    private String name;
    private List<Long> teamMembersIds;
    private Long tournamentId;
}
