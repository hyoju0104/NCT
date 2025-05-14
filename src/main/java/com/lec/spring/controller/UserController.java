package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Payment;
import com.lec.spring.domain.Plan;
import com.lec.spring.domain.User;
import com.lec.spring.repository.PlanRepository;
import com.lec.spring.service.PaymentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PlanRepository planRepository;
    private final PaymentService paymentService;

    public UserController(PlanRepository planRepository, PaymentService paymentService) {
        this.planRepository = planRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/mypage/detail")
    public void showMyPage(
            @AuthenticationPrincipal PrincipalDetails principalDetails, //로그인한 사용자 정보 가져오기
            Model model //뷰에 데이터 넘기기 위함
            ){
        //로그인한 사용자 정보 가져오기
        User user = principalDetails.getUser();
        //사용자의 구독 정보 설정(silver,gold,vip << Plan)
        if (user.getPlanId() != null) {
            Plan plan = planRepository.findByPlanId(user.getPlanId());
            user.setPlan(plan);
        }else {
            user.setPlan(new Plan()); //NPE방지
        }
        //Payment 정보 가져오기
        Payment payment = paymentService.findLatestByUserId(user.getId());
        // 💡 payment가 null일 수도 있으니 확인 후 model에 추가
        if (payment != null) {
            model.addAttribute("paidAt", payment.getPaidAt());
            model.addAttribute("expiredAt", payment.getExpiredAt());
        } else {
            model.addAttribute("paidAt", null);
            model.addAttribute("expiredAt", null);
        }

        model.addAttribute("user", user);
    }
}