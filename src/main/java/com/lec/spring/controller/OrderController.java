package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.User;
import com.lec.spring.service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ItemService itemService;
    // ====
    public OrderController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpSession session, @AuthenticationPrincipal PrincipalDetails principal) {
        Item item = itemService.detail(id);
//        User user = (User) session.getAttribute("user");

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
                                @RequestParam String phoneNum,
                                @RequestParam String address) {

        itemService.markAsUnavailable(id);

        return "redirect:/order/complete/" + id;
    }

}
