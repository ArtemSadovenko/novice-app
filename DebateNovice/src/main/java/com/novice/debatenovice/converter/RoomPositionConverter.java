package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.room.RoomPositionDTO;
import com.novice.debatenovice.entity.RoomPosition;
import com.novice.debatenovice.repository.RoomDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomPositionConverter {
    @Autowired
    private TeamConverter teamConverter;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    public RoomPositionDTO toDTO(RoomPosition roomPosition) {
        return RoomPositionDTO.builder()
                .roomDetailsId(roomPosition.getRoomDetails().getId())
                .position(roomPosition.getPosition())
                .team(teamConverter.toTeamDTO(roomPosition.getTeam()))
                .id(roomPosition.getId())
                .build();
    }

    public RoomPosition toEntity(RoomPositionDTO roomPositionDTO) {
        return RoomPosition.builder()
                .id(roomPositionDTO.getId())
                .position(roomPositionDTO.getPosition())
                .team(teamConverter.toTeam(roomPositionDTO.getTeam()))
                .roomDetails(roomDetailsRepository.findById(roomPositionDTO.getRoomDetailsId()).orElseThrow(() -> new IllegalArgumentException("Room Details Not Found")))
                .build();
    }
}
