package com.itss.auth.mapper;

import com.itss.auth.dto.response.PermissionResponse;
import com.itss.auth.entity.Permission;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public PermissionResponse toResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse permissionResponse = new PermissionResponse();

        permissionResponse.setDescription( permission.getDescription() );
        permissionResponse.setId( permission.getId() );
        permissionResponse.setName( permission.getName() );

        return permissionResponse;
    }
}
