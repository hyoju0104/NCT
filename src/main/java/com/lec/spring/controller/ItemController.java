package com.lec.spring.controller;

import ch.qos.logback.core.model.Model;
import com.lec.spring.domain.Item;
import com.lec.spring.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<Item> items = itemService.list();
//        model.addAttribute("items", items);
//        return "item/list";
//    }

}
