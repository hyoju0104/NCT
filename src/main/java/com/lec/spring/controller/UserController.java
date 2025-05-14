package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.*;
import com.lec.spring.repository.PlanRepository;
import com.lec.spring.service.PaymentService;
import com.lec.spring.service.PostService;
import com.lec.spring.service.RentalService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final PlanRepository planRepository;
    private final PaymentService paymentService;
    private final RentalService rentalService;
    private final PostService postService;


    public UserController(PlanRepository planRepository,
                          PaymentService paymentService,
                          RentalService rentalService,
                          PostService postService) {
        this.planRepository = planRepository;
        this.paymentService = paymentService;
        this.rentalService = rentalService;
        this.postService = postService;
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
        Plan plan = user.getPlan();
        int totalCnt = 0;
        // 총 대여 가능 횟수는 Plan type을 기준으로 계산
        if (plan != null && plan.getType() != null) {
            totalCnt = switch (plan.getType()) {
                case SILVER -> 3;
                case GOLD -> 5;
                case VIP -> 7;
            };
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

        // 대여 내역
        List<Rental> rentals = rentalService.findAllByUserId(user.getId());
        model.addAttribute("rentals", rentals);

        // 게시글 목록
        List<Post> posts = postService.findByUserId(user.getId());
        model.addAttribute("posts", posts);


        model.addAttribute("user", user);
    }
}