package com.example.projects.lovable_clone.mapper;


import com.example.projects.lovable_clone.entity.ProjectMember;
import com.example.projects.lovable_clone.entity.User;
import com.example.projects.lovable_clone.dto.member.MemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse toProjectMemberResponseFromOwner(User owner);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "name", source = "user.name")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);
//    List<MemberResponse> toProjectMemberResponseList(List<ProjectMember> projectMembers);
}
