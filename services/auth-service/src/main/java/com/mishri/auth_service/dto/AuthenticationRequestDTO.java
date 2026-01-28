package com.mishri.auth_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {
    @NotBlank(message = "Username is required")
    @Size(min=5 , max = 15, message = "Username must be between 5 and 15 char")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min=5 , max = 15, message = "Password must be between 5 and 15 char")
    private String password;
}
