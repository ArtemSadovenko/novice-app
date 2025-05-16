package com.novice.debatenovice.converter;

import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.UserPreviewDTO;
import com.novice.debatenovice.dto.auth.RegisterRequest;
import com.novice.debatenovice.entity.User;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserConverter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .experience(user.getExperience())
                .email(user.getEmail())
                .password(user.getPassword())
//                .createdAt(user.getCreatedAt())
                .averageScore(user.getAverageScore())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId() != null ? userDTO.getId() : null)
                .username(userDTO.getUsername())
                .roles(userDTO.getRoles())
                .experience(userDTO.getExperience())
                .email(userDTO.getEmail())
//                .createdAt(userDTO.getCreatedAt())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .averageScore(userDTO.getAverageScore())
                .build();

    }

    public User toEntityWithValidId(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new CustomException("Invalid user id:" + userDTO.getUsername());
        }
        return User.builder()
                .id(userDTO.getId() != null ? userDTO.getId() : null)
                .username(userDTO.getUsername())
                .roles(userDTO.getRoles())
                .experience(userDTO.getExperience())
                .email(userDTO.getEmail())
//                .createdAt(userDTO.getCreatedAt())
                .password(userDTO.getPassword())
                .averageScore(userDTO.getAverageScore())
                .build();

    }

    public User registerToUser(RegisterRequest registerRequest) {

        return User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getUsername())
                .experience(registerRequest.getExperience())
                .roles(new HashSet<>(registerRequest.getRoles()))
                .build();

    }

    public UserPreviewDTO entityToPreviewDTO(User user) {
        return UserPreviewDTO.builder()
                .averageScore(user.getAverageScore())
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .experience(user.getExperience())
                .build();
    }
}
