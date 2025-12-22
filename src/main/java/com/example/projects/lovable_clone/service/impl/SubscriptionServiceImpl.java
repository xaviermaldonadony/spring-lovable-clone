package com.example.projects.lovable_clone.service.impl;

import com.example.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.example.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.example.projects.lovable_clone.dto.subscription.PortalResponse;
import com.example.projects.lovable_clone.dto.subscription.SubscritionResponse;
import com.example.projects.lovable_clone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public SubscritionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSession(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
