package com.example.projects.lovable_clone.repository;

import com.example.projects.lovable_clone.entity.ChatSession;
import com.example.projects.lovable_clone.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
