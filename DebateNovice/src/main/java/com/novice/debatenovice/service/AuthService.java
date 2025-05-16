package com.novice.debatenovice.service;

import com.novice.debatenovice.converter.UserConverter;
import com.novice.debatenovice.dto.auth.LoginRequest;
import com.novice.debatenovice.dto.auth.RegisterRequest;
import com.novice.debatenovice.entity.User;
import com.novice.debatenovice.exeptions.CustomException;
import com.novice.debatenovice.repository.UserRepository;
import com.novice.debatenovice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserConverter userConverter;

    public Map<String, Object> login(LoginRequest loginRequest) {
        try {
            // Validate request data
            if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
                throw new CustomException("Username and password are required", HttpStatus.BAD_REQUEST);
            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Get the User entity to extract the ID
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate JWT token with user ID embedded
            String token = jwtUtil.generateToken(userDetails, user.getId());

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());


            return response;
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            throw new CustomException("Login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    public void register(RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getPassword() == null) {
            throw new CustomException("Email and password are required", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new CustomException("Email already exists", HttpStatus.CONFLICT);
        }


        userRepository.save(userConverter.registerToUser(registerRequest));
    }

    public void registerAll(List<RegisterRequest> registerRequest) {
        userRepository.saveAll(registerRequest.stream().map(userConverter::registerToUser).toList());
    }
}
