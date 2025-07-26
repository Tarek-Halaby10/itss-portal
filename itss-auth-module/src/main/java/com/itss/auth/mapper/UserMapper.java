// UserMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.UserResponse;
import com.itss.auth.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
	@Autowired
    private RoleMapper roleMapper;
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles() != null ? 
                		roleMapper.toResponseList(user.getRoles().stream().toList()) : 
                			new HashSet<>())
                .build();
    }

    public Set<UserResponse> toResponseList(List<User> users) {
        if (users == null) {
            return Set.of();
        }
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toSet());
    }

    public UserResponse toBasicResponse(User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus())
                .build();
    }
}