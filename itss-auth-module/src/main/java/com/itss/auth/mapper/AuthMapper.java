// AuthMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.AuthResponse;
import com.itss.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    @Autowired
    private UserMapper userMapper;

    public AuthResponse toAuthResponse(User user, String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .user(userMapper.toResponse(user))
                .build();
    }

    public AuthResponse toAuthResponse(User user, String accessToken, String refreshToken, long expiresIn) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .user(userMapper.toResponse(user))
                .build();
    }
}