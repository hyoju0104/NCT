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

    @GetMapping("/item/update/{id}")
    public String updateForm(@PathVariable Long id, Model model,
                             @AuthenticationPrincipal BrandDetails principal) {

        Item item = itemService.detail(id);

        if (!item.getBrand().getId().equals(principal.getBrand().getId())) {
            return "redirect:/brand/list";
        }

        model.addAttribute("item", item);
        return "brand/item/update";
    }

    @GetMapping("/item/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Item item = itemService.detail(id);
        model.addAttribute("item", item);
        return "brand/item/detail";
    }

    @PostMapping("/item/update")
    public String updateOk(@ModelAttribute Item item, Model model) {
        if (item.getIsExist() == null) {
            item.setIsExist(true);
        }

        int result = itemService.update(item);
        model.addAttribute("result", result);
        return "brand/item/updateOk";
    }
}
