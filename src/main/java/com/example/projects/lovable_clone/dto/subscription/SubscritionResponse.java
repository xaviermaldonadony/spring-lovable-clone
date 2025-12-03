package com.example.projects.lovable_clone.dto.subscription;

import java.time.Instant;

public record SubscritionResponse(
    PlanResponse plan,
    String status,
    Instant periodEnd,
    Long tokensUsedThisCycle
) {
}
