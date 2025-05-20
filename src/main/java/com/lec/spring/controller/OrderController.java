package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.OrderValidator;
import com.lec.spring.domain.Rental;
import com.lec.spring.domain.User;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ItemService itemService;
    private final OrderValidator orderValidator;
    private final RentalService rentalService;

    public OrderController(ItemService itemService, OrderValidator orderValidator, RentalService rentalService) {
        this.itemService = itemService;
        this.orderValidator = orderValidator;
        this.rentalService = rentalService;
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              HttpSession session,
                              @AuthenticationPrincipal PrincipalUserDetails principal) {
        Item item = itemService.detail(id);

        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "로그인 후 작성 가능합니다.");
            return "redirect:/post/list";
        }

        User user = principal.getUser();

        model.addAttribute("item", item);
        model.addAttribute("user", user);

        return "order/detail";
    }

    @GetMapping("/complete/{id}")
    public String orderComplete(@PathVariable Long id, Model model) {
        Item item = itemService.detail(id);
        model.addAttribute("item", item);
        return "order/complete";
    }

    @PostMapping("/complete/{id}")
    public String completeOrder(@PathVariable Long id,
                                @ModelAttribute("user") User user,
                                BindingResult result,
                                @AuthenticationPrincipal PrincipalUserDetails principal,
                                HttpSession session,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        User loginUser = principal.getUser();
        // 대여 가능 조건 검사
        if (!"ACTIVE".equals(loginUser.getStatusAccount())) {
            redirectAttributes.addFlashAttribute("inactiveError", "계정 비활성화 상태입니다.");
            return "redirect:/order/detail/" + id;
        }
        if(loginUser.getPlanId() == null) {
            redirectAttributes.addFlashAttribute("planNullError", "요금제를 구독해 주세요.");
            return "redirect:/order/detail/" + id;

        }


        orderValidator.validate(user, result);
        if (result.hasErrors()) {
            if (result.hasFieldErrors("phoneNum")) {
                model.addAttribute("error_phoneNum", result.getFieldError("phoneNum").getDefaultMessage());
            }
            if (result.hasFieldErrors("address")) {
                model.addAttribute("error_address", result.getFieldError("address").getDefaultMessage());
            }
            model.addAttribute("item", itemService.detail(id));
            return "order/detail";
        }


        Item item = itemService.detail(id);

        // Rental 생성
        Rental rental = new Rental();
        rental.setUser(principal.getUser());
        rental.setItem(itemService.detail(id));
        rental.setStatus("RENTED");

        // 저장 + available_count -1 처리
        rentalService.rentItem(rental);
        itemService.markAsUnavailable(item.getId());


        return "redirect:/order/complete/" + id;
    }

}
