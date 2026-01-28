package com.mishri.auth_service.controller;

import com.mishri.auth_service.config.security.JwtUtil;
import com.mishri.auth_service.dto.LoginRequestDTO;
import com.mishri.auth_service.dto.RegisterRequestDTO;
import com.mishri.auth_service.entity.Role;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.services.RegisterUserService;
import com.mishri.auth_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        User user = registerUserService.registerUser(registerRequestDTO.getEmail(),registerRequestDTO.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequestDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginRequestDTO.getEmail());

//        Set<Role> roles = userDetails.getAuthorities().stream().map(auth -> Role.valueOf(auth.getAuthority())).collect(Collectors.toSet());

        Set<Role> roles = userDetails.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .map(Role::valueOf)
                .collect(Collectors.toSet());


        String token = jwtUtil.generateToken(
                userDetails.getUsername(),
                roles);

        return ResponseEntity.ok(token);
    }
}
