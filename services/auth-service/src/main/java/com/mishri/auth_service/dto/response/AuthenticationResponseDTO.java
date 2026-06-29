package com.mishri.auth_service.dto.response;

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
    private Long id;

    private String email;

    private String token;

    @Builder.Default
    private String type = "Bearer";

    private List<String> roles;
}
