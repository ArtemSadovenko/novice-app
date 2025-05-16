package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.room.RoomDTO;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.dto.room.RoomPreviewDTO;
import com.novice.debatenovice.dto.table.RoundTableDTO;
import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.entity.RoomDetails;
import com.novice.debatenovice.entity.Tournament;
import com.novice.debatenovice.enums.POSITION;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoomDetailsConverter {

    @Autowired
    private RoundResultsConverter roundResultsConverter;
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private RoomPositionConverter roomPositionConverter;

    public RoomDetailsDTO toDTO(RoomDetails roomDetails) {
        return RoomDetailsDTO.builder()
                .id(roomDetails.getId())
                .roomName(roomDetails.getRoomName())
                .roundNumber(roomDetails.getRoundNumber())
                .roundStatus(roomDetails.getRoundStatus())
                .results(roomDetails.getResults() == null || roomDetails.getResults().isEmpty() ? new ArrayList<>() : roomDetails.getResults().stream().map(e -> roundResultsConverter.convertRoundResultsToDTO(e)).collect(Collectors.toList()))
                .judges(roomDetails.getJudges() == null || roomDetails.getJudges().isEmpty() ? new ArrayList<>() : roomDetails.getJudges().stream().map(e -> userConverter.toDTO(e)).collect(Collectors.toList()))
                .positions(roomDetails.getPositions() == null || roomDetails.getPositions().isEmpty() ? new ArrayList<>() : roomDetails.getPositions().stream().map(e -> roomPositionConverter.toDTO(e)).collect(Collectors.toList()))
                .tournamentId(roomDetails.getTournament().getId())
                .build();
    }

    public RoomDetails toEntity(RoomDetailsDTO dto) {
        return RoomDetails.builder()
                .id(dto.getId() == null ? null : dto.getId())
                .roundStatus(dto.getRoundStatus() == null ? null : dto.getRoundStatus())
                .roomName(dto.getRoomName() == null ? null : dto.getRoomName())
                .roundNumber(dto.getRoundNumber() == null ? null : dto.getRoundNumber())
                .judges(dto.getJudges() == null || dto.getJudges().isEmpty() ? new ArrayList<>() : dto.getJudges().stream().map(e -> userConverter.toEntity(e)).collect(Collectors.toList()))
                .positions(dto.getPositions() == null || dto.getPositions().isEmpty() ? new ArrayList<>() : dto.getPositions().stream().map(e -> roomPositionConverter.toEntity(e)).collect(Collectors.toList()))
                .tournament(tournamentRepository.findById(dto.getTournamentId()).orElseThrow(() -> new IllegalArgumentException("Tournament Not Found")))
                .results(dto.getResults() == null || dto.getResults().isEmpty()? new ArrayList<>(): dto.getResults().stream().map(roundResultsConverter::convertDTOToRoundResults).toList())
                .build();
    }


    public RoundTableDTO toTable(List<RoomDetailsDTO> roomDetailsDTOS) {
        List<RoomDTO> rooms = new ArrayList<>();
        roomDetailsDTOS.forEach(roomDetailsDTO -> {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomName(roomDetailsDTO.getRoomName());
            roomDTO.setRoomDetailsId(roomDetailsDTO.getId());
            roomDTO.setJudges(roomDetailsDTO.getJudges());
            Map<POSITION, TeamDTO> teams = new HashMap<>();
            roomDetailsDTO.getPositions().forEach(roomPositionDTO -> {
                teams.put(roomPositionDTO.getPosition(), roomPositionDTO.getTeam());
            });
            roomDTO.setTeams(teams);
            rooms.add(roomDTO);
        });

        return RoundTableDTO.builder()
                .tournamentId(roomDetailsDTOS.get(0).getTournamentId())
                .roundNumber(roomDetailsDTOS.get(0).getRoundNumber())
                .rooms(rooms)
                .build();
    }

    public RoomPreviewDTO toPreviewDTO(RoomDetailsDTO room) {
        Tournament tournament = tournamentRepository.findById(room.getTournamentId()).orElseThrow(() -> new CustomException("Tournament Not Found"));
        return  RoomPreviewDTO.builder()
                .id(room.getId())
                .roundNumber(room.getRoundNumber())
                .roomName(room.getRoomName())
                .tournamentId(room.getTournamentId())
                .tournamentName(tournament.getName())
                .build();
    }
}