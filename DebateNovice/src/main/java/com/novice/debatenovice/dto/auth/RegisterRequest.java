package com.novice.debatenovice.dto.auth;

import com.novice.debatenovice.enums.USER_EXPERIENCE;
import com.novice.debatenovice.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String username;
    @NonNull
    private USER_EXPERIENCE experience;
    @NonNull
    private List<USER_ROLE> roles;

}
