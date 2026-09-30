package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.chat.ChatResponse;
import com.example.projects.lovable_clone.entity.ChatMessage;
import com.example.projects.lovable_clone.entity.ChatSession;
import com.example.projects.lovable_clone.entity.ChatSessionId;
import com.example.projects.lovable_clone.mapper.ChatMapper;
import com.example.projects.lovable_clone.repository.ChatMessageRepository;
import com.example.projects.lovable_clone.repository.ChatSessionRepository;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatServiceImpl implements ChatService {

    ChatMessageRepository chatMessageRepository;
    ChatSessionRepository chatSessionRepository;
    ChatMapper chatMapper;
    AuthUtil authUtil;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        
        ChatSession chatSession = chatSessionRepository
                .getReferenceById(new ChatSessionId(projectId, userId));

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);
        
        return chatMapper.fromListOfChatMessages(chatMessageList);
    }
}