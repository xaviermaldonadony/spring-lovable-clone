package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.example.projects.lovable_clone.enums.SubscriptionStatus;

import java.time.Instant;

public interface SubscriptionService {
    boolean canCreateNewProject() {}

    SubscriptionResponse getCurrentSubscription();

    void activateSubscription(Long userId, Long planId,String subscriptionId, String customerId);

    void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periosStart, Instant periosEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String gatewaySubscriptionId);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptonPastDue(String gatewaySubscriptionId);
}
