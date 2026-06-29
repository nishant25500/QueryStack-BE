package com.mishri.auth_service.mapper;

import com.mishri.auth_service.dto.response.AuthenticationResponseDTO;
import com.mishri.auth_service.dto.response.RegistrationResponseDTO;
import com.mishri.auth_service.entity.Role;
import com.mishri.auth_service.entity.User;

public class AuthenticationMapper {

     public static RegistrationResponseDTO toRegistrationResponseDTO(User user){
         return RegistrationResponseDTO.builder()
                 .id(user.getId())
                 .email(user.getEmail())
                 .roles(user.getRoles().stream().map(Role::name).toList())
                 .build();
     }

    public static AuthenticationResponseDTO toAuthenticationResponseDTO(
            User user,
            String token) {

        return AuthenticationResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .roles(
                        user.getRoles()
                                .stream()
                                .map(Role::name)
                                .toList()
                )
                .build();
    }
}
