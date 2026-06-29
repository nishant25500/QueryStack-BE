package com.mishri.auth_service.controller;

import com.mishri.auth_service.dto.response.ApiResponse;
import com.mishri.auth_service.dto.response.UserProfileResponseDTO;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.mapper.UserProfileMapper;
import com.mishri.auth_service.services.RegisterUserService;
import com.mishri.auth_service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<ApiResponse<UserProfileResponseDTO>> getUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        log.info("Fetching user profile for user: {}", email);

        User user = userService.findByEmail(email);

        UserProfileResponseDTO response = UserProfileMapper.toUserProfileResponseDTO(user);

        return ResponseEntity.ok(
                ApiResponse.<UserProfileResponseDTO>builder()
                        .success(true)
                        .message("Profile fetched successfully")
                        .data(response)
                        .build()
        );
    }



}
