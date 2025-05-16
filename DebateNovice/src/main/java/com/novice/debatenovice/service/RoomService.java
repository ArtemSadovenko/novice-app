package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.RoomDetailsConverter;
import com.novice.debatenovice.converter.RoundResultsConverter;
import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.dto.room.RoomPreviewDTO;
import com.novice.debatenovice.entity.Tournament;
import com.novice.debatenovice.enums.ROUND_STATUS;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.RoomDetailsRepository;
import com.novice.debatenovice.repository.RoomPositionRepository;
import com.novice.debatenovice.repository.RoundResultsRepository;
import com.novice.debatenovice.service.interfaces.RoomServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService implements RoomServiceInterface {
    @Autowired
    private RoomPositionRepository roomPositionRepository;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;
    @Autowired
    private RoomDetailsConverter roomDetailsConverter;
    @Autowired
    private RoundResultsConverter roundResultsConverter;
    @Autowired
    private TeamService teamService;
    @Autowired
    private RoundResultsRepository roundResultsRepository;
    @Autowired
    private RoundResultsService roundResultsService;


    public List<RoomDetailsDTO> createRoomDetailsTamplate(Tournament tournament, Integer roomAmount, Integer roundNumber) {
        List<RoomDetailsDTO> rooms = new ArrayList<>();
        for (int i = 0; i < roomAmount; i++) {
            rooms.add(
                    RoomDetailsDTO.builder()
                            .roundNumber(roundNumber)
                            .tournamentId(tournament.getId())
                            .roundStatus(ROUND_STATUS.IN_PROGRESS)
                            .build()
            );
        }
        return createAll(rooms);
    }


    public List<RoomDetailsDTO> createAll(List<RoomDetailsDTO> rooms) {
        return roomDetailsRepository.saveAll(
                        rooms.stream()
                                .map(roomDetailsConverter::toEntity).collect(Collectors.toList()))
                .stream()
                .map(roomDetailsConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoomDetailsDTO getRoomDetails(Long id) {
        return roomDetailsConverter.toDTO(roomDetailsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Room not found")));
    }

    @Override
    public RoomDetailsDTO saveRoomDetails(RoomDetailsDTO roomDetails) {
        return roomDetailsConverter.toDTO(roomDetailsRepository.save(roomDetailsConverter.toEntity(roomDetails)));
    }

    @Override
    public RoomDetailsDTO updateRoomDetails(RoomDetailsDTO roomDetails) {
        return roomDetailsConverter.toDTO(roomDetailsRepository.save(roomDetailsConverter.toEntity(roomDetails)));
    }

    @Override
    public void deleteRoomDetails(Long id) {
        roomDetailsRepository.deleteById(id);
    }

    @Override
    public List<RoomDetailsDTO> getAllRoomDetails() {
        return roomDetailsRepository.findAll().stream().map(roomDetailsConverter::toDTO).collect(Collectors.toList());
    }

    public RoomDetailsDTO setRoomResult(Long roomId, List<RoundResultsDTO> roundResults) {
        RoomDetailsDTO details = getRoomDetails(roomId);
        Set<Long> originalTeamIds = details.getPositions().stream().map(e -> e.getTeam().getId()).collect(Collectors.toSet());
        Set<Long> resultTeams = roundResults.stream().map(e -> e.getTeam().getId()).collect(Collectors.toSet());
        List<RoundResultsDTO> results = new ArrayList<>();
        if (details.getRoundStatus().equals(ROUND_STATUS.COMPLETED)) {
            throw new CustomException("Room Already Completed");
        }
        if (resultTeams.containsAll(originalTeamIds)) {

            roundResults.forEach(e -> e.setRoomDetailsId(roomId));
            roundResults.forEach(e -> e.setTeam(teamService.findTeamById(e.getTeam().getId())));
            results.addAll(roundResults);
            results = roundResultsService.createAll(results);
            details.getResults().addAll(results);
            details.getResults().forEach(e ->
                    e.getTeam()
                            .setTeamPoints(
                                    e.getTeam().getTeamPoints()
                                            .add(BigDecimal
                                                    .valueOf(e.getPlace().getPoints()))));
            details.getPositions().forEach(e ->
                    e.getTeam().setTeamPoints(
                            details.getResults()
                                    .stream()
                                    .filter(k ->
                                            k.getTeam().getId() == e.getTeam().getId())
                                    .findFirst().get()
                                    .getTeam().getTeamPoints()));
            teamService.createAll(details.getResults().stream().map(e -> e.getTeam()).collect(Collectors.toList()));
            details.setRoundStatus(ROUND_STATUS.COMPLETED);
            updateRoomDetails(details);
        } else {
            throw new CustomException("Not all teams mentioned in results", HttpStatus.BAD_REQUEST);
        }
        return updateRoomDetails(details);
    }

    public List<RoomDetailsDTO> getJudgeRooms(UserDTO user) {
        return  getAllRoomDetails().stream().filter(room -> room.getRoundStatus().equals(ROUND_STATUS.IN_PROGRESS) && room.getJudges().stream().map(j -> j.getId()).anyMatch(user.getId()::equals)).collect(Collectors.toList());
    }

    public List<RoomPreviewDTO> getJudgeRoomsPreview(UserDTO user) {
        return  getAllRoomDetails().stream().filter(room -> room.getRoundStatus().equals(ROUND_STATUS.IN_PROGRESS) && room.getJudges().stream().map(j -> j.getId()).anyMatch(user.getId()::equals)).collect(Collectors.toList()).stream().map(e -> roomDetailsConverter.toPreviewDTO(e)).collect(Collectors.toList());

    }
}