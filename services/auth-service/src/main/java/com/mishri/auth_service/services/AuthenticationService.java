package com.mishri.auth_service.services;

import com.mishri.auth_service.config.security.JwtUtil;
import com.mishri.auth_service.dto.response.AuthenticationResponseDTO;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * Authenticates the user and returns JWT along with user details.
     */
    public AuthenticationResponseDTO login(String email, String password) {

        // Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Fetch authenticated user
        User user = userService.findByEmail(email);

        // Generate JWT
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRoles()
        );

        // Convert to response DTO
        return AuthenticationMapper.toAuthenticationResponseDTO(user, token);
    }
}