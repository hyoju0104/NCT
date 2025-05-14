package com.lec.spring.service;

import com.lec.spring.domain.Payment;
import com.lec.spring.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    // PaymentRepository를 주입받아서 사용할 준비
    private final PaymentRepository paymentRepository;

    // userId로 최근 결제 정보 1개 가져오기
    @Override
    public Payment findLatestByUserId(Long userId) {
        return paymentRepository.findLatestByUserId(userId);
    }
}
