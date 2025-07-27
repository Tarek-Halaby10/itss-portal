package com.itss.auth.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
public class UserInviteRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private Long roleId;
} 
