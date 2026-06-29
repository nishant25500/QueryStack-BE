package com.mishri.auth_service.controller;

import com.mishri.auth_service.dto.request.LoginRequestDTO;
import com.mishri.auth_service.dto.request.RegisterRequestDTO;
import com.mishri.auth_service.dto.response.ApiResponse;
import com.mishri.auth_service.dto.response.AuthenticationResponseDTO;
import com.mishri.auth_service.dto.response.RegistrationResponseDTO;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.mapper.AuthenticationMapper;
import com.mishri.auth_service.services.AuthenticationService;
import com.mishri.auth_service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponseDTO>> registerUser(
            @Valid @RequestBody RegisterRequestDTO request) {

        User user = userService.registerUser(
                request.getEmail(),
                request.getPassword()
        );

        RegistrationResponseDTO response =
                AuthenticationMapper.toRegistrationResponseDTO(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RegistrationResponseDTO>builder()
                                .success(true)
                                .message("User registered successfully")
                                .data(response)
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponseDTO>> loginUser(
            @Valid @RequestBody LoginRequestDTO request) {

        AuthenticationResponseDTO response =
                authenticationService.login(
                        request.getEmail(),
                        request.getPassword()
                );

        return ResponseEntity.ok(
                ApiResponse.<AuthenticationResponseDTO>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );
    }
}