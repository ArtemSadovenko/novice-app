package com.novice.debatenovice.controller;

import com.novice.debatenovice.annotations.CurrentUser;
import com.novice.debatenovice.annotations.ResponsibleJudgeAccessOnly;
import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.room.RoomDetailsDTO;
import com.novice.debatenovice.dto.room.RoomPreviewDTO;
import com.novice.debatenovice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-details")
public class RoomDetailsController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDetailsDTO>> getAllRoomDetails() {
        return new ResponseEntity<>(roomService.getAllRoomDetails(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsDTO> getRoomDetails(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomDetails(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDetailsDTO> createRoomDetails(@RequestBody RoomDetailsDTO roomDetailsDTO) {
        return new ResponseEntity<>(roomService.saveRoomDetails(roomDetailsDTO), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<RoomDetailsDTO> updateRoomDetails(@RequestBody RoomDetailsDTO roomDetailsDTO) {
        return new ResponseEntity<>(roomService.updateRoomDetails(roomDetailsDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomDetails(@PathVariable Long id) {
        roomService.deleteRoomDetails(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{roomId}")
    @ResponsibleJudgeAccessOnly(roomIdParam = "roomId")
    public RoomDetailsDTO setRoomResult(@PathVariable Long roomId, @RequestBody List<RoundResultsDTO> roundResults) {
        return roomService.setRoomResult(roomId, roundResults);
    }

    @GetMapping("/judge-rooms")
    public ResponseEntity< List<RoomDetailsDTO>> getJudgeRooms(@CurrentUser UserDTO user) {
        return new ResponseEntity<>(roomService.getJudgeRooms(user), HttpStatus.OK);
    }

    @GetMapping("/judge-preview")
    public ResponseEntity<List<RoomPreviewDTO>> getJudgePreview(@CurrentUser UserDTO user) {
        return new ResponseEntity<>(roomService.getJudgeRoomsPreview(user), HttpStatus.OK);
    }


}
