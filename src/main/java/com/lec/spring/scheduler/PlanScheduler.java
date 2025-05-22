package com.lec.spring.scheduler;

import com.lec.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanScheduler {
    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void planScheduler() {
        System.out.println("스케줄러 실행 중...");
        userService.updateRentalCnt();
        userService.updatePlan();
    }
}
