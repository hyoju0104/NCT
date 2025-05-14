package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.OrderValidator;
import com.lec.spring.domain.User;
import com.lec.spring.service.ItemService;
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

    public OrderController(ItemService itemService, OrderValidator orderValidator) {
        this.itemService = itemService;
        this.orderValidator = orderValidator;
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              HttpSession session,
                              @AuthenticationPrincipal PrincipalDetails principal) {
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
                                @AuthenticationPrincipal PrincipalDetails principal,
                                Model model) {

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

        int updateResult = itemService.markAsUnavailable(id);
        System.out.println(">> markAsUnavailable 결과: " + updateResult);

        return "redirect:/order/complete/" + id;
    }
}
