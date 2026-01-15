package com.example.projects.lovable_clone.controller;


import com.example.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.example.projects.lovable_clone.dto.member.MemberResponse;
import com.example.projects.lovable_clone.dto.member.updateMemberRoleRequest;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {

    // Constructor dependency
    // the requiredArgs constructor is responsible for it.
    ProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getProjectMembers(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));
    }

    @PostMapping
    public ResponseEntity<MemberResponse> inviteMember(
            @PathVariable Long projectId,
            @RequestBody @Valid InviteMemberRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectMemberService.inviteMember(projectId, request)
        );
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            @RequestBody @Valid updateMemberRoleRequest request
    ){
        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, request));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ){
        projectMemberService.removeProjectMember(projectId, memberId);

        return ResponseEntity.noContent().build();
    }
}
