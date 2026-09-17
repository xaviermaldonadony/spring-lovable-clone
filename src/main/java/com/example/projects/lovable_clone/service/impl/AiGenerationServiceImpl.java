package com.example.projects.lovable_clone.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiGenerationServiceImpl implements AiGenerationService {

//    private final StreamingChatModel chatModel;

    @Override
    public Flux<String> streamResponse(String message, Long projectId) {
//        UserMessage userMessage = new UserMessage(message);
//        Prompt prompt = new Prompt(userMessage);
//
//        return Flux.fromStream(chatModel.stream(prompt).content());
        return null;
    }
}
