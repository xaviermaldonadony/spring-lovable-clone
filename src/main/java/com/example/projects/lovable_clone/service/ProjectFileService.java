package com.example.projects.lovable_clone.service;


import com.example.projects.lovable_clone.dto.project.FileContentResponse;
import com.example.projects.lovable_clone.dto.project.FileNode;

import java.util.List;

public interface ProjectFileService {
    List<FileNode> getFileTree(Long projectId);

    FileContentResponse getFileContent (Long projectId, String path);

    void saveFile(Long projectId, String path, String content);
}
