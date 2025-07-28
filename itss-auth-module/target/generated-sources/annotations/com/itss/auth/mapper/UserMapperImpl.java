package com.itss.auth.mapper;

import com.itss.auth.dto.request.UserRegistrationRequest;
import com.itss.auth.dto.response.UserResponse;
import com.itss.auth.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-28T19:00:29+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.50.v20250628-1110, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRegistrationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( request.getEmail() );
        user.password( request.getPassword() );
        user.username( request.getUsername() );

        return user.build();
    }

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setEmail( user.getEmail() );
        userResponse.setId( user.getId() );
        if ( user.getStatus() != null ) {
            userResponse.setStatus( user.getStatus().name() );
        }
        userResponse.setUsername( user.getUsername() );

        userResponse.setRoles( mapRolesToStrings(user.getRoles()) );

        return userResponse;
    }
}
