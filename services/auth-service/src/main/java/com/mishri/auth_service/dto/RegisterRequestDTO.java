package com.mishri.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

//    @NotBlank(message = "Username is required")
//    @Size(min=5 , max = 15, message = "Username must be between 5 and 15 char")
////    private String username;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=5 , max = 15, message = "Password must be between 5 and 15 char")
    private String password;

//    private Set<String> roles;
}
