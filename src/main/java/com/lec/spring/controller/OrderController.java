package com.lec.spring.controller;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.User;
import com.lec.spring.service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ItemService itemService;

    public OrderController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model, HttpSession session) {
        Item item = itemService.detail(id);
        User user = (User) session.getAttribute("user");

        model.addAttribute("item", item);
        model.addAttribute("user", user);

        return "order/detail";
    }

}
