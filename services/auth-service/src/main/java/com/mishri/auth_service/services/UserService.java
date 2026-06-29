package com.mishri.auth_service.services;

import com.mishri.auth_service.entity.Role;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.exception.UserAlreadyExistsException;
import com.mishri.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     */
    public User registerUser(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Set.of(Role.USER))
                .build();

        return userRepository.save(user);
    }

    /**
     * Returns user by email.
     */
    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}