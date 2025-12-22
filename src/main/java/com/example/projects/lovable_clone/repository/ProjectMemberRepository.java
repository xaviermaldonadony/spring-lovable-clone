package com.example.projects.lovable_clone.repository;

import com.example.projects.lovable_clone.entity.ProjectMember;
import com.example.projects.lovable_clone.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    // Will get all project members that have this projectId
    List<ProjectMember> findByIdProjectId(Long projectId);
}
