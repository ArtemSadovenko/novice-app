package com.novice.debatenovice.service.interfaces;

import com.novice.debatenovice.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user);

    void deleteUser(Long id);

    UserDTO getUser(Long id);

    List<UserDTO> getAllUsers();
}
