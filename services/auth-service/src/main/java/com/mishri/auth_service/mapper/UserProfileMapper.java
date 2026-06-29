package com.mishri.auth_service.mapper;

import com.mishri.auth_service.dto.response.UserProfileResponseDTO;
import com.mishri.auth_service.entity.Role;
import com.mishri.auth_service.entity.User;

public class UserProfileMapper {

    public static UserProfileResponseDTO toUserProfileResponseDTO(User user){
        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(Role::name)
                                .toList()
                )
                .build();
    }
}
