package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.example.projects.lovable_clone.dto.member.MemberResponse;
import com.example.projects.lovable_clone.dto.member.updateMemberRoleRequest;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {
    @Nullable List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, updateMemberRoleRequest request);

    void removeProjectMember(Long projectId, Long memberId);
}
