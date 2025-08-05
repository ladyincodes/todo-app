package com.ladyincodes.todoapi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email (message = "Please enter a valid email address")
    @NotBlank (message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size (min=6, message = "Password must be at least 6 characters long")
    private String password;
}
