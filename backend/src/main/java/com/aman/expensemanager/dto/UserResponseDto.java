package com.aman.expensemanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

public class UserResponseDto {

    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    private Boolean enabled;
    private LocalDateTime createdAt;

    @NotBlank(message = "Password is required")
    private String password;

    // getters and setters
}