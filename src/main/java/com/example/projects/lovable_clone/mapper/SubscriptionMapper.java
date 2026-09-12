package com.example.projects.lovable_clone.mapper;

import com.example.projects.lovable_clone.dto.subscription.PlanResponse;
import com.example.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import com.example.projects.lovable_clone.entity.Plan;
import com.example.projects.lovable_clone.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}
