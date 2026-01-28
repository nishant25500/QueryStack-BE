package com.mishri.auth_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank
//    @Size(min = 5, max = 15)
//    @Column(unique = true)
//    private String username;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;


//    @Size(min = 6, max = 15)
    @NotBlank
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles" , joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role" , nullable = false)
    private Set<Role> roles;
}
