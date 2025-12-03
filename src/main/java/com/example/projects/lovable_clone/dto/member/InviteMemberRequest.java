package com.example.projects.lovable_clone.dto.member;

import com.example.projects.lovable_clone.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
