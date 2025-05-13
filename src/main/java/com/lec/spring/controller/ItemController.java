package com.lec.spring.controller;

import com.lec.spring.domain.Item;
import com.lec.spring.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("items", itemService.list());
        return "item/list";
    }

    @GetMapping("/category/{category}")
    public String listByCategory(@PathVariable String category, Model model) {
        model.addAttribute("items", itemService.findByCategory(category));
        return "item/list";
    }

//    @GetMapping("/list")
//    public String list(Model model) {
//        List<Item> items = itemService.list();
//        model.addAttribute("items", items);
//        return "item/list";
//    }

}
