package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.entity.*;
import com.example.projects.lovable_clone.enums.ChatEventType;
import com.example.projects.lovable_clone.enums.MessageRole;
import com.example.projects.lovable_clone.error.ResourceNotFoundException;
import com.example.projects.lovable_clone.llm.LlmResponseParser;
import com.example.projects.lovable_clone.llm.PromptUtils;
import com.example.projects.lovable_clone.llm.advisors.FileTreeContextAdvisor;
import com.example.projects.lovable_clone.llm.tools.CodeGenerationTools;
import com.example.projects.lovable_clone.repository.ChatEventRepository;
import com.example.projects.lovable_clone.repository.ChatMessageRepository;
import com.example.projects.lovable_clone.repository.ChatSessionRepository;
import com.example.projects.lovable_clone.repository.ProjectRepository;
import com.example.projects.lovable_clone.repository.UserRepository;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {
    private final UserRepository userRepository;

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final ChatSessionRepository chatSessionRepository;
    private final ProjectRepository projectRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatEventRepository chatEventRepository;
    private final LlmResponseParser llmResponseParser;

    private static final Pattern FILE_TAG_PATTERN = Pattern.compile(" <file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<String> streamResponse(String userMessage, Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        ChatSession chatSession = createChatSessionIfNotExists(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
                "userId", userId,
                "projectId", projectId
        );
        StringBuilder fullResponseBuffer = new StringBuilder();

        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectFileService, projectId);

        AtomicReference<Long> startTime = new AtomicReference<>(System.currentTimeMillis());
        AtomicReference<Long> endTime = new AtomicReference<>();

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .tools(codeGenerationTools)
                .advisors(advisorSpec -> {
                            advisorSpec.params(advisorParams);
                            advisorSpec.advisors(fileTreeContextAdvisor);
                        }
                )
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();

                    if(content != null && !content.isEmpty() && endTime.get() == 0) {// first non empty chunk received
                        endTime.set(System.currentTimeMillis());
                    }
                    fullResponseBuffer.append(content);
                })
                .doOnComplete(() -> {
//                    Without the scheduler, this would block the reactive thread, potentially causing performance issues
//                    By scheduling it on boundedElastic(), the blocking work runs on a separate thread pool designed for that purpose
                    Schedulers.boundedElastic().schedule(() -> {
//                        parseAndSaveFiles(fullResponseBuffer.toString(), projectId);
                        long duration = (endTime.get() - startTime.get()) / 1000;
                        finalizeChats(userMessage, chatSession, fullResponseBuffer.toString(), duration);
                    });
                })
                .doOnError(error -> {
                    log.error("Error during streaming for projectId: {}", projectId);
                })
                .map(response -> Objects.requireNonNull(response.getResult().getOutput().getText()));
    }

    private void finalizeChats(String userMessage, ChatSession chatSession, String fullText, long duration) {
        Long projectId = chatSession.getProject().getId();

        // save the user message
        chatMessageRepository.save(
                ChatMessage.builder()
                        .chatSession(chatSession)
                        .role(MessageRole.USER)
                        .content(userMessage)
                        .build());

        ChatMessage assistantChatMessage = chatMessageRepository.save(
                ChatMessage.builder()
                        .role(MessageRole.ASSISTANT)
                        .content("Assistant Message...")
                        .chatSession(chatSession)
                        .content(fullText)
                        .build());
        // Parse and save chat events
        List<ChatEvent> chatEventList = llmResponseParser.parseChatEvents(fullText, assistantChatMessage);
        chatEventList.addFirst(0, ChatEvent.builder()
                .type(ChatEventType.THOUGHT)
                .chatMessage(assistantChatMessage)
                .content("Thought for " + duration + "s")
                .sequenceOrder(0)
                .build()
        );

        chatEventList.stream()
                .filter(chatEvent -> chatEvent.getType() == ChatEventType.FILE_EDIT)
                .forEach(chatEvent -> projectFileService.saveFile(
                        projectId,
                        chatEvent.getFilePath(),
                        chatEvent.getContent()
                ));

        chatEventRepository.saveAll(chatEventList);
    }


    private ChatSession createChatSessionIfNotExists(Long projectId, Long userId) {
        ChatSessionId chatSessionId = new ChatSessionId(projectId, userId);
        ChatSession chatSession = chatSessionRepository.findById(chatSessionId).orElse(null)

        if (chatSession == null) {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("project", projectId.toString()));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("user", userId.toString()));

            chatSession = ChatSession.builder()
                    .id(chatSessionId)
                    .project(project)
                    .user(user)
                    .build();

            chatSessionRepository.save(chatSession);
        }
        return chatSession;
    }
}
