package com.itss.auth.service;

import com.itss.auth.dto.request.*;
import com.itss.auth.entity.User;
import java.util.List;

public interface UserService {
    User registerUser(UserRegistrationRequest request);
    void approveUser(Long userId);
    void declineUser(Long userId);
    void blockUser(Long userId);
    void inviteUser(UserInviteRequest request, String invitedByUsername);
    void resetPasswordRequest(PasswordResetRequest request);
    void resetPassword(PasswordResetConfirmRequest request);
    List<User> getAllUsers();
    List<User> getPendingUsers();
    List<User> getBlockedUsers();
    User getUserById(Long id);
} 
