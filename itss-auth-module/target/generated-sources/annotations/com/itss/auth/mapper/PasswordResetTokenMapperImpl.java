package com.itss.auth.mapper;

import com.itss.auth.dto.response.PasswordResetTokenResponse;
import com.itss.auth.entity.PasswordResetToken;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T01:43:44+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PasswordResetTokenMapperImpl implements PasswordResetTokenMapper {

    @Override
    public PasswordResetTokenResponse toResponse(PasswordResetToken token) {
        if ( token == null ) {
            return null;
        }

        PasswordResetTokenResponse passwordResetTokenResponse = new PasswordResetTokenResponse();

        passwordResetTokenResponse.setId( token.getId() );
        passwordResetTokenResponse.setToken( token.getToken() );
        passwordResetTokenResponse.setExpiresAt( token.getExpiresAt() );
        passwordResetTokenResponse.setCreatedAt( token.getCreatedAt() );
        passwordResetTokenResponse.setUsed( token.isUsed() );

        passwordResetTokenResponse.setUser( mapUserToString(token.getUser()) );

        return passwordResetTokenResponse;
    }
}
