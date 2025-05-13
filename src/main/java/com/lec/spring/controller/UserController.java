package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Plan;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PlanRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PlanRepository planRepository;

    public UserController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @GetMapping("/mypage/detail")
    public void showMyPage(
            @AuthenticationPrincipal PrincipalDetails principalDetails, //로그인한 사용자 정보 가져오기
            Model model //뷰에 데이터 넘기기 위함
            ){
        User user = principalDetails.getUser();
        if (user.getPlanId() != null) {
            Plan plan = planRepository.findByPlanId(user.getPlanId());
            user.setPlan(plan);  // ⭐ 여기서 Plan 객체 넣어줘야 Thymeleaf에서 .plan.grade 접근 가능
        }else {
            user.setPlan(new Plan()); // 빈 Plan 넣어줘야 템플릿에서 NPE 안 남
        }

        model.addAttribute("user", user);
    }
}