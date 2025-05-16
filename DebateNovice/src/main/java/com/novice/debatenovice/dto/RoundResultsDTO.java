package com.novice.debatenovice.dto;

import com.novice.debatenovice.dto.team.TeamDTO;
import com.novice.debatenovice.enums.PLACE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoundResultsDTO {
    private Long id;
    private PLACE place;
    private Map<Long, Integer> speakerPoints; //<UserId, speaker point>
    private Long roomDetailsId;
    private TeamDTO team;
}