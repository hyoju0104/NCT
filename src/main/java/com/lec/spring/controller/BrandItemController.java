package com.lec.spring.controller;

import com.lec.spring.config.BrandDetails;
import com.lec.spring.domain.Brand;
import com.lec.spring.domain.Item;
import com.lec.spring.service.ItemService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandItemController {

    private final ItemService itemService;

    public BrandItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/write")
    public String write(Model model) {
        model.addAttribute("item", new Item());
        return "brand/item/write";
    }

    @PostMapping("/item/write")
    public String writeOk(@ModelAttribute Item item,
                          @AuthenticationPrincipal BrandDetails principal) {

        Brand brand = principal.getBrand();
        item.setBrand(brand);
        itemService.save(item);
        return "redirect:/brand/list";
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        List<Item> itemList = itemService.findByBrandId(brandId);
        model.addAttribute("itemList", itemList);
        return "brand/list";
    }
}
