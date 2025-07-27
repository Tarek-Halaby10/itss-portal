package com.itss.auth.mapper;

import com.itss.auth.entity.PasswordResetToken;
import com.itss.auth.entity.User;
import com.itss.auth.dto.response.PasswordResetTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PasswordResetTokenMapper {
    PasswordResetTokenMapper INSTANCE = Mappers.getMapper(PasswordResetTokenMapper.class);

    @Mapping(target = "user", expression = "java(mapUserToString(token.getUser()))")
    PasswordResetTokenResponse toResponse(PasswordResetToken token);

    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
} 
