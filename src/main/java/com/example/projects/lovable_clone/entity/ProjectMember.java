package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.enums.ProjectRole;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectMember {

    ProjectMember id;

    Project project;

    User user;

    ProjectRole role;

    Instant invitedAt;

    Instant acceptedAt;
}
