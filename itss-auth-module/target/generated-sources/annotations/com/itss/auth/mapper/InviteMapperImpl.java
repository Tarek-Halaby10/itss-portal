package com.itss.auth.mapper;

import com.itss.auth.dto.response.InviteResponse;
import com.itss.auth.entity.Invite;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class InviteMapperImpl implements InviteMapper {

    @Override
    public InviteResponse toResponse(Invite invite) {
        if ( invite == null ) {
            return null;
        }

        InviteResponse inviteResponse = new InviteResponse();

        inviteResponse.setCreatedAt( invite.getCreatedAt() );
        inviteResponse.setEmail( invite.getEmail() );
        inviteResponse.setExpiresAt( invite.getExpiresAt() );
        inviteResponse.setId( invite.getId() );
        if ( invite.getStatus() != null ) {
            inviteResponse.setStatus( invite.getStatus().name() );
        }

        inviteResponse.setRole( mapRoleToString(invite.getRole()) );
        inviteResponse.setInvitedBy( mapUserToString(invite.getInvitedBy()) );

        return inviteResponse;
    }
}
