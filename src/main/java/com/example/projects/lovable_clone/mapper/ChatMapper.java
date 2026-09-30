package com.example.projects.lovable_clone.mapper;

import com.example.projects.lovable_clone.dto.chat.ChatEventResponse;
import com.example.projects.lovable_clone.dto.chat.ChatResponse;
import com.example.projects.lovable_clone.entity.ChatEvent;
import com.example.projects.lovable_clone.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatEventResponse toChatEventResponse(ChatEvent chatEvent);

    List<ChatResponse> fromListOfChatMessages(List<ChatMessage> chatMessages);
}
