package com.novice.debatenovice.controller;

import com.novice.debatenovice.annotations.AdminOnlyAccess;
import com.novice.debatenovice.dto.UserDTO;
import com.novice.debatenovice.dto.UserPreviewDTO;
import com.novice.debatenovice.security.JwtUtil;
import com.novice.debatenovice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @AdminOnlyAccess
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        UserDTO result = userService.updateUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<UserDTO>> createAll(@RequestBody List<UserDTO> users) {
        return new ResponseEntity<>(userService.createAll(users), HttpStatus.CREATED);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String jwt = (String) request.getAttribute("jwt");

        if (jwt == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
            }
        }

        if (jwt != null) {
            Long userId = jwtUtil.extractUserId(jwt);
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.badRequest().body("JWT token not found");
    }

    @GetMapping("/all-preview")
    public ResponseEntity<List<UserPreviewDTO>> getAllUsersWithPreview() {
        List<UserPreviewDTO> users = userService.getAllUsersPreview();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
