package com.itss.auth.service;

import com.itss.auth.entity.RefreshToken;
import com.itss.auth.entity.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void revokeTokensByUser(User user);
    void revokeToken(String token);
    boolean isTokenValid(String token);
    void cleanupExpiredTokens();
} 