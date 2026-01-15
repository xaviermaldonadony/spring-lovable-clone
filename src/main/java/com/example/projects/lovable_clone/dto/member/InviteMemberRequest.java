package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.enums.ProjectRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record InviteMemberRequest(
        @Email @NotNull
        String username,

        @NotNull
        ProjectRole role
) {
}
