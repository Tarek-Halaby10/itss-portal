package com.itss.auth.mapper;

import com.itss.auth.dto.response.PasswordResetTokenResponse;
import com.itss.auth.entity.PasswordResetToken;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PasswordResetTokenMapperImpl implements PasswordResetTokenMapper {

    @Override
    public PasswordResetTokenResponse toResponse(PasswordResetToken token) {
        if ( token == null ) {
            return null;
        }

        PasswordResetTokenResponse passwordResetTokenResponse = new PasswordResetTokenResponse();

        passwordResetTokenResponse.setCreatedAt( token.getCreatedAt() );
        passwordResetTokenResponse.setExpiresAt( token.getExpiresAt() );
        passwordResetTokenResponse.setId( token.getId() );
        passwordResetTokenResponse.setToken( token.getToken() );
        passwordResetTokenResponse.setUsed( token.isUsed() );

        passwordResetTokenResponse.setUser( mapUserToString(token.getUser()) );

        return passwordResetTokenResponse;
    }
}
