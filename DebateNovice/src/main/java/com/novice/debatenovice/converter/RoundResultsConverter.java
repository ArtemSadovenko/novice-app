package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.RoundResultsDTO;
import com.novice.debatenovice.entity.RoundResults;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.RoomDetailsRepository;
import com.novice.debatenovice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoundResultsConverter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TeamConverter teamConverter;

    @Autowired
    private RoomDetailsRepository roomDetailsRepository;

    public RoundResultsDTO convertRoundResultsToDTO(RoundResults roundResults) {
        return RoundResultsDTO.builder()
                .id(roundResults.getId())
                .place(roundResults.getPlace())
                .speakerPoints(roundResults.getSpeakerPoints())
                .team(teamConverter.toTeamDTO(roundResults.getTeam()))
                .roomDetailsId(roundResults.getRoomDetails().getId())
                .build();
    }

    public RoundResults convertDTOToRoundResults(RoundResultsDTO roundResultsDTO) {
        return RoundResults.builder()
                .id(roundResultsDTO.getId())
                .place(roundResultsDTO.getPlace())
                .roomDetails(roomDetailsRepository.findById(roundResultsDTO.getRoomDetailsId()).orElseThrow(() -> new CustomException("No room details found")))
                .speakerPoints(roundResultsDTO.getSpeakerPoints())
                .team(teamConverter.toTeam(roundResultsDTO.getTeam()))
                .build();
    }

//    public RoundResultsDTO toDTO(RoundResultsDTO result, Long roomId) {
//        return result.s;
//
//    }
}

