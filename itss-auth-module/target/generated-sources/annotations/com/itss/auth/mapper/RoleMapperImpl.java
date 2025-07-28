package com.itss.auth.mapper;

import com.itss.auth.dto.request.RoleCreateRequest;
import com.itss.auth.dto.request.RoleUpdateRequest;
import com.itss.auth.dto.response.RoleResponse;
import com.itss.auth.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.description( request.getDescription() );
        role.name( request.getName() );

        return role.build();
    }

    @Override
    public Role toEntity(RoleUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.description( request.getDescription() );
        role.name( request.getName() );

        return role.build();
    }

    @Override
    public RoleResponse toResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse roleResponse = new RoleResponse();

        roleResponse.setDescription( role.getDescription() );
        roleResponse.setId( role.getId() );
        roleResponse.setName( role.getName() );

        roleResponse.setPermissions( mapPermissionsToStrings(role.getPermissions()) );

        return roleResponse;
    }
}
