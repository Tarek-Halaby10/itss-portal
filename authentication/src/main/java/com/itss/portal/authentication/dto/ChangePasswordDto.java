package com.itss.portal.authentication.dto;

import com.itss.portal.authentication.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotBlank
    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
