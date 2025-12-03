package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.enums.MessageRole;

import java.time.Instant;

public class ChatMessage {
    Long id;

    ChatSession content;

    MessageRole role;

    String toolCalls; // Json array of Tools called

    Integer tokensUsed;

    Instant createdAt;
}
