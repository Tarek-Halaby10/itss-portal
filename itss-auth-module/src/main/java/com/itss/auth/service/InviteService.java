package com.itss.auth.service;

import com.itss.auth.dto.request.UserInviteRequest;
import com.itss.auth.entity.Invite;
import java.util.Optional;

public interface InviteService {
    void createInvite(UserInviteRequest request, String invitedByUsername);
    Optional<Invite> getInviteByToken(String token);
    void acceptInvite(String token, String password);
    void declineInvite(String token);
} 
