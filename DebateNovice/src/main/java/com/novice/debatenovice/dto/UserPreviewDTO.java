package com.novice.debatenovice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novice.debatenovice.enums.USER_EXPERIENCE;
import com.novice.debatenovice.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreviewDTO {
    private Long id;
    private Double averageScore;
    private String username;
    private String email;
    private USER_EXPERIENCE experience;
}
