// UserActivityMapper.java
package com.itss.auth.mapper;

import com.itss.auth.dto.response.UserActivityResponse;
import com.itss.auth.entity.UserActivity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserActivityMapper {


    public UserActivityResponse toResponse(UserActivity activity) {
        if (activity == null) {
            return null;
        }

        return UserActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUser() != null ? activity.getUser().getId() : null)
                .username(activity.getUser() != null ? activity.getUser().getUsername() : null)
                .userFullName(activity.getUser() != null ? 
                    activity.getUser().getFirstName() + " " + activity.getUser().getLastName() : null)
                .action(activity.getAction())
                .resource(activity.getResource())
                .resourceId(activity.getResourceId())
                .details(activity.getDetails())
                .ipAddress(activity.getIpAddress())
                .userAgent(activity.getUserAgent())
                .success(activity.getSuccess())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public UserActivityResponse toBasicResponse(UserActivity activity) {
        if (activity == null) {
            return null;
        }

        return UserActivityResponse.builder()
                .id(activity.getId())
                .action(activity.getAction())
                .resource(activity.getResource())
                .details(activity.getDetails())
                .success(activity.getSuccess())
                .createdAt(activity.getCreatedAt())
                .build();
    }

    public List<UserActivityResponse> toResponseList(List<UserActivity> activities) {
        if (activities == null) {
            return List.of();
        }
        return activities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}