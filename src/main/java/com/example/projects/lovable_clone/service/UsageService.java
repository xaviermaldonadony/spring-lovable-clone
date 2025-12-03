package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.example.projects.lovable_clone.dto.subscription.UsageTodayResponse;
import org.jspecify.annotations.Nullable;

public interface UsageService {
    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
