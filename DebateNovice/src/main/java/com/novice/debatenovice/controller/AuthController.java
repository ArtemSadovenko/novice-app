package com.novice.debatenovice.controller;

import com.novice.debatenovice.dto.auth.LoginRequest;
import com.novice.debatenovice.dto.auth.RegisterRequest;
import com.novice.debatenovice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/registerAll")
    public ResponseEntity<?> registerAll(@RequestBody List<RegisterRequest> registerRequest) {
        authService.registerAll(registerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
