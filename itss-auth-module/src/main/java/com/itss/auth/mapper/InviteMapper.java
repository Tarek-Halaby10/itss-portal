package com.itss.auth.mapper;

import com.itss.auth.entity.Invite;
import com.itss.auth.entity.Role;
import com.itss.auth.entity.User;
import com.itss.auth.dto.response.InviteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InviteMapper {
    InviteMapper INSTANCE = Mappers.getMapper(InviteMapper.class);

    @Mapping(target = "role", expression = "java(mapRoleToString(invite.getRole()))")
    @Mapping(target = "invitedBy", expression = "java(mapUserToString(invite.getInvitedBy()))")
    InviteResponse toResponse(Invite invite);

    default String mapRoleToString(Role role) {
        return role != null ? role.getName() : null;
    }

    default String mapUserToString(User user) {
        return user != null ? user.getUsername() : null;
    }
} 
