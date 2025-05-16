package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.TournamentDTO;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.entity.Tournament;
import com.novice.debatenovice.entity.User;
import com.novice.debatenovice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TournamentConverter {
    @Autowired
    private RoomDetailsConverter roomDetailsConverter;
    @Autowired
    private TeamConverter teamConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;

    public TournamentDTO toDTO(Tournament tournament) {
        return TournamentDTO.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .roomDetails(tournament.getRoomDetails() != null ? tournament.getRoomDetails().stream().map(roomDetailsConverter::toDTO).collect(Collectors.toList()) : null)
                .teams(tournament.getTeams() != null ? tournament.getTeams().stream().map(teamConverter::toTeamDTO).collect(Collectors.toList()) : null)
                .judges(tournament.getJudges().stream().map(userConverter::toDTO).collect(Collectors.toList()))
                .tournamentStatus(tournament.getTournamentStatus())
                .topics(tournament.getTopics())
                .build();
    }

    public Tournament toEntity(TournamentDTO dto) {
        List<User> judges = userRepository.findAllById(dto.getJudges().stream().map(UserDTO::getId).collect(Collectors.toList()));
        return Tournament.builder()
                .name(dto.getName())
                .tournamentStatus(dto.getTournamentStatus())
                .teams(dto.getTeams().stream().map(teamConverter::toTeam).collect(Collectors.toList()))
                .id(dto.getId())
                .tournamentStatus(dto.getTournamentStatus())
                .topics(dto.getTopics())
                .roomDetails(dto.getRoomDetails() != null ? dto.getRoomDetails().stream().map(roomDetailsConverter::toEntity).collect(Collectors.toList()) : null)
                .teams(dto.getTeams() != null ? dto.getTeams().stream().map(teamConverter::toTeam).collect(Collectors.toList()) : null)
//                .judges(dto.getJudges().stream().map(userConverter::toEntity).collect(Collectors.toList()))
                .judges(judges)
                .build();
    }
}
