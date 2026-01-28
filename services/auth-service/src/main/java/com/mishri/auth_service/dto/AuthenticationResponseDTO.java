package com.mishri.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {

    private String token;

    @Builder.Default
    private String type = "Bearer";

    private Long id;

    private String username;

    private List<String> roles;
}
