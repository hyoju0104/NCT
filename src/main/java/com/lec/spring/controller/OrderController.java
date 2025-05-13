package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.User;
import com.lec.spring.service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ItemService itemService;

    public OrderController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Item item = itemService.detail(id);
//        User user = (User) session.getAttribute("user");
        
        // 1) 로그인 체크
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "로그인 후 작성 가능합니다.");
            return "redirect:/post/list";
        }
        // 2) User 정보 가져오기
        User user = principal.getUser();

        model.addAttribute("item", item);
        model.addAttribute("user", user);

        return "order/detail";
    }

}
