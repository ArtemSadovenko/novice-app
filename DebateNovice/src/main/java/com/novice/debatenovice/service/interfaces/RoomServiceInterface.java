package com.novice.debatenovice.service.interfaces;

import com.novice.debatenovice.dto.room.RoomDetailsDTO;

import java.util.List;

public interface RoomServiceInterface {
    RoomDetailsDTO getRoomDetails(Long id);

    RoomDetailsDTO saveRoomDetails(RoomDetailsDTO roomDetails);

    RoomDetailsDTO updateRoomDetails(RoomDetailsDTO roomDetails);

    void deleteRoomDetails(Long id);

    List<RoomDetailsDTO> getAllRoomDetails();


}
