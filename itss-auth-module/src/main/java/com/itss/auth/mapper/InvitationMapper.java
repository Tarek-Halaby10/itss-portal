// InvitationMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.InvitationResponse;
import com.itss.auth.entity.InvitationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvitationMapper {

    @Autowired
    private RoleMapper roleMapper;


    public InvitationResponse toResponse(InvitationToken invitation) {
        if (invitation == null) {
            return null;
        }

        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getEmail())
                .role(invitation.getRole() != null ? roleMapper.toBasicResponse(invitation.getRole()) : null)
                .expiresAt(invitation.getExpiresAt())
                .used(invitation.getUsed())
                .acceptedAt(invitation.getAcceptedAt())
                .createdAt(invitation.getCreatedAt())
                .createdByName(invitation.getCreatedBy() != null ? 
                    invitation.getCreatedBy().getUsername() : null)
                .isExpired(invitation.isExpired())
                .build();
    }

    public List<InvitationResponse> toResponseList(List<InvitationToken> invitations) {
        if (invitations == null) {
            return List.of();
        }
        return invitations.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public InvitationResponse toBasicResponse(InvitationToken invitation) {
        if (invitation == null) {
            return null;
        }

        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getEmail())
                .used(invitation.getUsed())
                .expiresAt(invitation.getExpiresAt())
                .createdAt(invitation.getCreatedAt())
                .isExpired(invitation.isExpired())
                .build();
    }
}