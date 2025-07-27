package com.itss.auth.service.impl;

import com.itss.auth.entity.RefreshToken;
import com.itss.auth.entity.User;
import com.itss.auth.exception.CustomExceptions;
import com.itss.auth.repository.RefreshTokenRepository;
import com.itss.auth.security.JwtUtil;
import com.itss.auth.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDurationMs;

    @Override
    public RefreshToken createRefreshToken(User user) {
        // Revoke any existing refresh tokens for this user (single session per user)
        revokeTokensByUser(user);

        // Generate new JWT refresh token
        String jwtToken = jwtUtil.generateRefreshToken(user.getUsername(), Map.of());
        
        // Create and save refresh token entity
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(jwtToken)
                .expiresAt(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (!token.isValid()) {
            refreshTokenRepository.delete(token);
            throw new CustomExceptions.TokenExpiredException("Refresh token is expired or revoked");
        }
        return token;
    }

    @Override
    public void revokeTokensByUser(User user) {
        refreshTokenRepository.revokeAllTokensByUser(user);
    }

    @Override
    public void revokeToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            RefreshToken rt = refreshToken.get();
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTokenValid(String token) {
        return refreshTokenRepository.existsByTokenAndRevokedFalse(token) && jwtUtil.validateToken(token);
    }

    @Override
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(Instant.now());
    }
} 