package com.itss.auth.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class PasswordResetRequest {
    @NotBlank
    @Email
    private String email;
} 
