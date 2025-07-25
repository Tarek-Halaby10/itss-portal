package com.itss.portal.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateDto {
    @NotBlank @Email
    private String email;

    @NotBlank @jakarta.validation.constraints.Size(min=4, max=32)
    private String username;
}
