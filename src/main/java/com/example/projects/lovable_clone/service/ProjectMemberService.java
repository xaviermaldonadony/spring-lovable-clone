package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.example.projects.lovable_clone.dto.member.MemberResponse;
import com.example.projects.lovable_clone.dto.member.updateMemberRoleRequest;
import com.example.projects.lovable_clone.entity.ProjectMember;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMember> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

    MemberResponse updateMemberRole(Long projectId, Long memberId, updateMemberRoleRequest request, Long userId);

    MemberResponse delteProjectMember(Long projectId, Long memberId, Long userId);
}
