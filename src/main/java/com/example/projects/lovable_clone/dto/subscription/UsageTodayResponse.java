package com.example.projects.lovable_clone.dto.subscription;

public record UsageTodayResponse(
        int tokenUsed,
        int tokensLimit,
        int previewsRunning,
        int previewLimit
) {
}
