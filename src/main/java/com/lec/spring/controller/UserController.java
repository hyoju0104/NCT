package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.*;
import com.lec.spring.repository.PlanRepository;
import com.lec.spring.service.PaymentService;
import com.lec.spring.service.PostService;
import com.lec.spring.service.RentalService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lec.spring.domain.UserUpdateValidator;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PlanRepository planRepository;
    private final PaymentService paymentService;
    private final RentalService rentalService;
    private final PostService postService;
    private final UserService userService;
    private final UserUpdateValidator userUpdateValidator;

    public UserController(PlanRepository planRepository,
                          PaymentService paymentService,
                          RentalService rentalService,
                          PostService postService,
                          UserService userService,
                          UserUpdateValidator userUpdateValidator) {
        this.planRepository = planRepository;
        this.paymentService = paymentService;
        this.rentalService = rentalService;
        this.postService = postService;
        this.userService = userService;
        this.userUpdateValidator = userUpdateValidator;
    }

    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userUpdateValidator);
    }


    @GetMapping("/mypage/detail")
    public void showMyPage(
            @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, //로그인한 사용자 정보 가져오기
            Model model //뷰에 데이터 넘기기 위함
    ) {
        //로그인한 사용자 정보 가져오기
        Long id = principalUserDetails.getUser().getId();
        User user = userService.findById(id);
        //사용자의 구독 정보 설정(silver,gold,vip << Plan)
        if (user.getPlanId() != null) {
            Plan plan = planRepository.findByPlanId(user.getPlanId());
            user.setPlan(plan);
        } else {
            user.setPlan(new Plan()); //NPE방지
        }

        Plan plan = user.getPlan();
        int totalCnt = 0;
        // 총 대여 가능 횟수는 Plan type을 기준으로 계산
        if (plan != null && plan.getType() != null) {
            totalCnt = switch (plan.getType()) {
                case "SILVER" -> 3;
                case "GOLD" -> 5;
                case "VIP" -> 10;
                default -> 0;
            };
        }

        //Payment 정보 가져오기
        Payment payment = paymentService.findLatestByUserId(user.getId());
        // 💡 payment가 null일 수도 있으니 확인 후 model에 추가
        if (payment != null) {
            model.addAttribute("paidAt", payment.getPaidAt());

            // ✅ 구독 만료일 계산 (30일 후)
            LocalDateTime expiredAt = payment.getPaidAt().plusDays(30);
            payment.setExpiredAt(expiredAt);
            model.addAttribute("expiredAt", payment.getExpiredAt());
        } else {
            model.addAttribute("paidAt", null);
            model.addAttribute("expiredAt", null);
        }

        // 대여 내역
        List<Rental> rentals = rentalService.findAllByUserId(id);
        model.addAttribute("rentals", rentals);

        //대여할때마다 -1씩 카운트
        model.addAttribute("totalCnt", totalCnt);
        int usedCnt = user.getRentalCnt();
        int remainingCnt = totalCnt - usedCnt;

        model.addAttribute("usedCnt", usedCnt);
        model.addAttribute("remainingCnt", remainingCnt);


        List<Post> myPosts = postService.findByUserId(user.getId());
        model.addAttribute("myPosts", myPosts);


        model.addAttribute("user", user);
    }

    @PostMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal PrincipalUserDetails principal) {
        Long userId = principal.getUser().getId();
        userService.markAsDeleted(userId);  // status_account = 'DELETED'
        return "redirect:/logout"; // 로그아웃으로 강제 이동
    }


    @GetMapping("/mypage/update")
    public void updateForm(
            @AuthenticationPrincipal PrincipalUserDetails principal,
            Model model
    ) {
        User user = userService.findById(principal.getUser().getId());
        model.addAttribute("user", user);
    }

    @PostMapping("/mypage/update")
    public String updateSubmit(@ModelAttribute("user") @Valid User user,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "user/mypage/update";
        }

        userService.updateUserInfo(user);
        return "redirect:/user/mypage/detail";

    }


    @GetMapping("/payment")
    public String paymentForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                              RedirectAttributes redirectAttrs) {
        User user = userService.findById(principalUserDetails.getUser().getId());
        Payment payment = paymentService.findLatestByUserId(user.getId());
        if (payment != null && payment.getPaidAt().plusDays(30).isAfter(LocalDateTime.now())) {
            redirectAttrs.addFlashAttribute("error", "구독 만료 후에만 변경 가능합니다.");
            return "redirect:/user/mypage/detail";
        }
        return "user/payment";
    }

        @PostMapping("/payment")
        public String submitPayment (
                @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                @RequestParam("planId") Long planId,
                HttpServletRequest request,
                RedirectAttributes redirectAttrs
    ){
            Long id = principalUserDetails.getUser().getId();

            Payment payment = paymentService.findLatestByUserId(id);
            if (payment != null
                    && payment.getPaidAt().plusDays(30).isAfter(LocalDateTime.now())) {
                    redirectAttrs.addFlashAttribute("error", "구독 만료 후에만 변경 가능합니다.");
                return "redirect:/user/mypage/detail";
            }
            // 1. planId만 따로 업데이트
            userService.updateUserPlanId(id, planId);

        // 2. 결제 처리
        userService.createPayment(id);

        // 3. status_plan 업데이트
        userService.updateStatusPlan(id, planId);

        return "redirect:/user/mypage/detail";
    }


        @GetMapping("/mypage/point")
        public void pointRefundForm (
                @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                Model model
    ){
            User user = userService.findById(principalUserDetails.getUser().getId());
            model.addAttribute("user", user);
            model.addAttribute("refundForm", new RefundForm());
        }

        @PostMapping("/mypage/point")
        public String refundPoint (
                @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                @Valid @ModelAttribute("refundForm") RefundForm refundForm,
                BindingResult bindingResult,
                RedirectAttributes redirectAttributes,
                Model model
    ){
            User user = userService.findById(principalUserDetails.getUser().getId());
            model.addAttribute("user", user);

            if (bindingResult.hasErrors()) {
                return "user/mypage/point";
            }

            Integer amount = refundForm.getAmount();

            if (user.getPoint() == null || user.getPoint() < amount) {
                redirectAttributes.addFlashAttribute("error", "포인트가 부족합니다.");
                return "redirect:/user/mypage/point";
            }

            try {
                userService.refundPoint(user.getId(), amount);
                user.setPoint(user.getPoint() - amount);
                redirectAttributes.addFlashAttribute("success", "환급 요청이 정상적으로 처리되었습니다.");
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }

            return "redirect:/user/mypage/point";

        }

    }
