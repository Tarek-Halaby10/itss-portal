package com.itss.auth.mapper;

import com.itss.auth.dto.response.InviteResponse;
import com.itss.auth.entity.Invite;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-27T23:39:45+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class InviteMapperImpl implements InviteMapper {

    @Override
    public InviteResponse toResponse(Invite invite) {
        if ( invite == null ) {
            return null;
        }

        InviteResponse inviteResponse = new InviteResponse();

        inviteResponse.setId( invite.getId() );
        inviteResponse.setEmail( invite.getEmail() );
        if ( invite.getStatus() != null ) {
            inviteResponse.setStatus( invite.getStatus().name() );
        }
        inviteResponse.setExpiresAt( invite.getExpiresAt() );
        inviteResponse.setCreatedAt( invite.getCreatedAt() );

        inviteResponse.setRole( mapRoleToString(invite.getRole()) );
        inviteResponse.setInvitedBy( mapUserToString(invite.getInvitedBy()) );

        return inviteResponse;
    }
}
