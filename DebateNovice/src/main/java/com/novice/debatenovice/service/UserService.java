package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.UserConverter;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.UserPreviewDTO;
import com.novice.debatenovice.repository.UserRepository;
import com.novice.debatenovice.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDTO createUser(UserDTO user) {
        return userConverter.toDTO(userRepository.save(userConverter.toEntity(user)));
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        return userConverter.toDTO(userRepository.save(userConverter.toEntity(user)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> createAll(List<UserDTO> users) {
        return userRepository.saveAll(users.stream().map(userConverter::toEntity).collect(Collectors.toList())).stream().map(userConverter::toDTO).collect(Collectors.toList());

    }

    @Override
    public UserDTO getUser(Long id) {
        return userConverter.toDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserDTO getUserByEmail(String email) {
        return userConverter.toDTO(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(x -> userConverter.toDTO(x)).collect(Collectors.toList());
    }

    public List<UserPreviewDTO> getAllUsersPreview() {
        return userRepository.findAll().stream().map(x -> userConverter.entityToPreviewDTO(x)).collect(Collectors.toList());
    }
}
