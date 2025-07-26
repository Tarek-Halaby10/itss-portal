// AuthResponse.java - Place in: itss-auth-module/src/main/java/com/itss/auth/dto/response/
package com.itss.auth.dto.response;

import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserResponse user;
    private Set<String> permissions;
    
}