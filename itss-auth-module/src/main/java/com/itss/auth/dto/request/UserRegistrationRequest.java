package com.itss.auth.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.itss.auth.validation.UniqueEmail;
import com.itss.auth.validation.UniqueUsername;

@Data
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @UniqueUsername
    private String username;

    @NotBlank
    @Email
    @Size(max = 100)
    @UniqueEmail
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
} 
