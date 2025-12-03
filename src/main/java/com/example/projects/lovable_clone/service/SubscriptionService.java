package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.example.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.example.projects.lovable_clone.dto.subscription.PortalResponse;
import com.example.projects.lovable_clone.dto.subscription.SubscritionResponse;

public interface SubscriptionService {
    SubscritionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSession(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
