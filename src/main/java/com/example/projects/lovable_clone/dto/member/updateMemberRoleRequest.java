package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record updateMemberRoleRequest(
        @NotNull
        ProjectRole role
) {
}
