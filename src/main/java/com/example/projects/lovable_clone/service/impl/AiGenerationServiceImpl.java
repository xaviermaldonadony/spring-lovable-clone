package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.llm.PromptUtils;
import com.example.projects.lovable_clone.security.AuthUtil;
import com.example.projects.lovable_clone.service.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {

    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private static final Pattern FILE_TAG_PATTERN = Pattern.compile(" <file path=\"([^\"]+)\">(.*?)</file>", Pattern.DOTALL);

    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<String> streamResponse(String userMessage, Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        createChatSessonIfNotExists(projectId, userId);

        StringBuilder fullResponseBugger = new StringBuilder();

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(userMessage)
                .advisors(advisorSpec -> {
                    advisorSpec.param("userId", userId);
                    advisorSpec.param("projectId", projectId);
                })
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();
                    fullResponseBugger.append(content);
                })
                .doOnComplete(() -> {
//                    Without the scheduler, this would block the reactive thread, potentially causing performance issues
//                    By scheduling it on boundedElastic(), the blocking work runs on a separate thread pool designed for that purpose
                    Schedulers.boundedElastic().schedule(() -> {
                        parseAndSaveFiles(fullResponseBugger.toString(), projectId);
                    });
                })
                .doOnError(error -> {
                    log.error("Stream error: {}", projectId);
                })
                .map(response -> Objects.requireNonNull(response.getResult().getOutput().getText()));
    }

    private void parseAndSaveFiles(String fullResponse, Long projectId) {

        String dummy = """
                <message>
                I'm going to read the files and generate the code
                </message>
               <file path="src/App.jsx">
                import App from './App.jsx'
               </file> 
                <message>
                I'm going to read the files and generate the code
                </message>
               <file path="src/App.jsx">
                import App from './App.jsx'
               </file>
                """;
        // goal is to parse

        Matcher matcher = FILE_TAG_PATTERN.matcher(fullResponse);
        while (matcher.find()) {
            String filePath = matcher.group(1);
            String fileContent = matcher.group(2).trim();
            projectFileService.saveFile(projectId, filePath, fileContent);
        }
    }

    private void createChatSessonIfNotExists(Long projectId, Long userId) {

    }
}
