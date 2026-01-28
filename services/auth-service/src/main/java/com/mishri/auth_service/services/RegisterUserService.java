package com.mishri.auth_service.services;

import com.mishri.auth_service.entity.Role;
import com.mishri.auth_service.entity.User;
import com.mishri.auth_service.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password){
        if(userRepository.existsByEmail(email)){
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRoles(Set.of(Role.USER));

        return userRepository.save(newUser);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
