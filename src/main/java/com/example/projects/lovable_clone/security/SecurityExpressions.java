package com.example.projects.lovable_clone.security;

import com.example.projects.lovable_clone.enums.ProjectPermission;
import com.example.projects.lovable_clone.repository.ProjectMemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


@Component("security")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SecurityExpressions {

    private ProjectMemberRepository projectMemberRepository;
    private AuthUtil authUtil;

    public boolean canViewProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW);
    }


    public boolean canEditProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean canDeleteProject(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean canViewMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean canManageMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }

    private boolean hasPermission(Long projectId, ProjectPermission projectPremission){
       Long userId = authUtil.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.getPermissions().contains(projectPremission))
                .orElse(false);
    }
}
