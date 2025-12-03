package com.example.projects.lovable_clone.entity;

import com.example.projects.lovable_clone.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subscription {
    Long id;
//    @ManyToOne // Many users can have one subscription, one subscription can have many users
    User user;
    Plan plan;
    SubscriptionStatus status;

    String stripeCustomerId;
//  User can see subscription status in strip dashboard
    String stripeSubscriptionId;

    // will change periodically if subscription active
    Instant currentPeriodStart;
    Instant currentPeriodEnd;

    Boolean cancelAtPeriodEnd = false;

    Instant createdAt;
    Instant updatedAt;
}
