package com.lec.spring.repository;

import com.lec.spring.domain.Plan;

public interface PlanRepository {
    Plan findByPlanId(Long planId);
}
