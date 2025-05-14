package com.lec.spring.service;

import com.lec.spring.domain.Payment;

public interface PaymentService {
    // userId에 해당하는 최신 결제 정보를 가져오는 메서드
    Payment findLatestByUserId(Long userId);
}

