package com.novice.debatenovice.service.interfaces;

import com.novice.debatenovice.dto.team.TeamDTO;

import java.util.List;

public interface TeamServiceInterface {
    TeamDTO findTeamById(Long id);

    TeamDTO createTeam(TeamDTO team);

    TeamDTO updateTeam(TeamDTO team);

    void deleteTeam(Long id);

    List<TeamDTO> findAllTeams();
}
