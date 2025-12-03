package com.example.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Project {
    long id;
    String name;
    User owner;
    Boolean isPublic;

    Instant createdAt;
    Instant updatedAt;
    Instant deletedAt;
}
