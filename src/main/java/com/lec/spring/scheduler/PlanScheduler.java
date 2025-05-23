package com.lec.spring.scheduler;

import com.lec.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanScheduler {
    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")   // 매일 00시 00분 00초에 스케줄러 실행
    public void planScheduler() {
        System.out.println("스케줄러 실행 중...");

        // user의 rental_cnt 초기화
        userService.updateRentalCnt();

        // user의 plan_status, plan_id, paid_at 초기화
        userService.updatePlan();
    }
}
