package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.enums.PreviewStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Preview {
    Long id;

    Project project;

//    this is for kubernetes
    String namespace;
    String podName;
    String previewUrl;

    PreviewStatus status;

    Instant startedAt;
    Instant terminatedAt;

    Instant createdAt;
}
