package com.example.projects.lovable_clone.service;


import com.example.projects.lovable_clone.dto.project.FileContentResponse;
import com.example.projects.lovable_clone.dto.project.FileNode;

import java.util.List;

public interface ProjectFileService {
    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFileContent (Long projectId, String path, Long userId);

    void saveFile(Long projectId, String path, String content);
}
