package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.TeamConverter;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.team.SimpleRegisterTeamDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.entity.Team;
import com.novice.debatenovice.entity.User;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.TeamRepository;
import com.novice.debatenovice.repository.TournamentRepository;
import com.novice.debatenovice.repository.UserRepository;
import com.novice.debatenovice.service.interfaces.TeamServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService implements TeamServiceInterface {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamConverter teamConverter;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TeamDTO findTeamById(Long id) {
        return teamConverter.toTeamDTO(teamRepository.findById(id).orElseThrow(() -> new CustomException("Team not found", HttpStatus.BAD_REQUEST)));
    }

    @Override
    public TeamDTO createTeam(TeamDTO team) {
        TeamUniqueOrElseThrow(team.getName(), team.getTournamentId(),
                userRepository.findAllById(
                        team.getTeamMembers().stream()
                                .map(UserDTO::getId).collect(Collectors.toList())));
        return teamConverter.toTeamDTO(teamRepository.save(teamConverter.toTeam(team)));
    }

    private boolean isTeamNameUnique(String teamName, Long tournamentId) {
        List<Team> tournamentTeams = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new CustomException("Tournament not found", HttpStatus.BAD_REQUEST))
                .getTeams();
        return tournamentTeams.stream().noneMatch(team -> team.getName().equals(teamName));
    }

    private boolean isTeamMembersUnique(List<User> members, Long tournamentId) {

        List<User> tournamentTeams = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new CustomException("Tournament not found", HttpStatus.BAD_REQUEST))
                .getTeams().stream().flatMap(e -> e.getUsers().stream()).collect(Collectors.toList());

        return members.stream()
                .noneMatch(member -> tournamentTeams.stream()
                        .anyMatch(teamMember -> teamMember.getId().equals(member.getId())));
    }

    private void TeamUniqueOrElseThrow(String teamName, Long tournamentIdteamId, List<User> tournamentTeams) {
        if (!isTeamNameUnique(teamName, tournamentIdteamId)) {
            throw new CustomException("Team already exists", HttpStatus.CONFLICT);
        }
        if (!isTeamMembersUnique(tournamentTeams, tournamentIdteamId)) {
            throw new CustomException("User is in another team", HttpStatus.CONFLICT);
        }
    }

    public TeamDTO createTeam(SimpleRegisterTeamDTO teamDTO) {

        TeamUniqueOrElseThrow(teamDTO.getName(), teamDTO.getTournamentId(),
                userRepository.findAllById(teamDTO.getTeamMembersIds()));
        return teamConverter.toTeamDTO(teamRepository.save(teamConverter.toTeam(teamDTO)));

    }

    public List<TeamDTO> createAll(List<TeamDTO> teams) {
        return teamRepository.saveAll(teams.stream().map(teamConverter::toTeam).collect(Collectors.toList())).stream().map(teamConverter::toTeamDTO).collect(Collectors.toList());
    }

    @Override
    public TeamDTO updateTeam(TeamDTO team) {
        TeamUniqueOrElseThrow(team.getName(), team.getTournamentId(),
                userRepository.findAllById(
                        team.getTeamMembers().stream()
                                .map(UserDTO::getId).collect(Collectors.toList())));
        return teamConverter.toTeamDTO(teamRepository.save(teamConverter.toTeam(team)));
    }

    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public List<TeamDTO> findAllTeams() {
        return teamRepository.findAll().stream().map(teamConverter::toTeamDTO).collect(Collectors.toList());
    }
}
