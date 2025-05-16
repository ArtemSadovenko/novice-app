package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.team.SimpleRegisterTeamDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.entity.Team;
import com.novice.debatenovice.entity.User;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.TournamentRepository;
import com.novice.debatenovice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private UserRepository userRepository;

    public TeamDTO toTeamDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .teamPoints(team.getTeamPoints())
                .teamMembers(team.getUsers().stream().map(e -> userConverter.toDTO(e)).collect(Collectors.toList()))
                .tournamentId(team.getTournament().getId())
                .build();
    }

    public Team toTeam(TeamDTO teamDTO) {
        List<User> teamMembers;
        try {
            //TODO this will cause problems with speaker point writings
            teamMembers = userRepository.findAllById(teamDTO.getTeamMembers().stream().map(UserDTO::getId).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new CustomException("Users not found: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return Team.builder()
                .id(teamDTO.getId() != null ? teamDTO.getId() : null)
                .name(teamDTO.getName())
//                .users(teamDTO.getTeamMembers().stream().map(e -> userConverter.toEntity(e)).collect(Collectors.toList()))
                .users(teamMembers)
                .teamPoints(teamDTO.getTeamPoints())
                .tournament(tournamentRepository.findById(teamDTO.getTournamentId()).orElse(null))
                .build();
    }

    public Team toTeam(SimpleRegisterTeamDTO teamDTO) {
        List<User> teamMembers;
        try {
            teamMembers = userRepository.findAllById(teamDTO.getTeamMembersIds());
        } catch (Exception e) {
            throw new CustomException("Users not found: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return Team.builder()
                .id(teamDTO.getId() != null ? teamDTO.getId() : null)
                .name(teamDTO.getName())
//                .users(teamDTO.getTeamMembers().stream().map(e -> userConverter.toEntity(e)).collect(Collectors.toList()))
                .users(teamMembers)
                .tournament(tournamentRepository.findById(teamDTO.getTournamentId()).orElse(null))
                .build();
    }
}
