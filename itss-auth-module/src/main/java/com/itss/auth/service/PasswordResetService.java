package com.itss.auth.service;

import com.itss.auth.dto.request.PasswordResetRequest;
import com.itss.auth.dto.request.PasswordResetConfirmRequest;

public interface PasswordResetService {
    void requestPasswordReset(PasswordResetRequest request);
    void confirmPasswordReset(PasswordResetConfirmRequest request);
} 
