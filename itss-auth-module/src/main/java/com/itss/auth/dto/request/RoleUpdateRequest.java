package com.itss.auth.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class RoleUpdateRequest {
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 255)
    private String description;

    private List<Long> permissionIds;
} 
