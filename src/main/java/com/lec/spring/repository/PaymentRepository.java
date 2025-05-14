package com.lec.spring.repository;

import com.lec.spring.domain.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentRepository {

    // userId 기준으로 가장 최근 결제(Payment)를 가져오는 메서드
    Payment findLatestByUserId(Long userId);

    int save(Payment payment);
}
