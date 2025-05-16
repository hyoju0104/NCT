package com.lec.spring.controller;

import com.lec.spring.config.BrandDetails;
import com.lec.spring.domain.Brand;
import com.lec.spring.domain.BrandItemValidator;
import com.lec.spring.domain.Item;
import com.lec.spring.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String writeOk(@ModelAttribute("item") Item item,
                          BindingResult result,
                          @AuthenticationPrincipal BrandDetails principal,
                          RedirectAttributes redirectAttributes,
                          Model model) {

        new BrandItemValidator().validate(item, result);

        if (result.hasErrors()) {

            result.getFieldErrors().forEach(error ->
                    model.addAttribute("error_" + error.getField(), error.getDefaultMessage())
            );
            model.addAttribute("item", item);
            return "/brand/item/write";
        }

        item.setBrand(principal.getBrand());
        item.setIsExist(true);
        itemService.save(item);

        model.addAttribute("item", item);
        model.addAttribute("result", 1);

        return "brand/item/writeOk";
    }

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        List<Item> itemList = itemService.findByBrandId(brandId);
        model.addAttribute("itemList", itemList);
        return "brand/list";
    }

    @GetMapping("/item/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Item item = itemService.detail(id);
        model.addAttribute("item", item);
        return "brand/item/detail";
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

    @PostMapping("/item/update")
    public String updateOk(@ModelAttribute Item item,
                           BindingResult result,
                           @AuthenticationPrincipal BrandDetails principal,
                           Model model) {

        new BrandItemValidator().validate(item, result);

        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error ->
                    model.addAttribute("error_" + error.getField(), error.getDefaultMessage())
            );
            item.setBrand(principal.getBrand());
            model.addAttribute("item", item);
            return "brand/item/update";
        }

        item.setBrand(principal.getBrand());
        if (item.getIsExist() == null) item.setIsExist(true);

        int updated = itemService.update(item);
        model.addAttribute("result", updated);
        return "brand/item/updateOk";
    }

    @PostMapping("/item/delete")
    public String deleteItem(@RequestParam Long id,
                             @AuthenticationPrincipal BrandDetails principal,
                             Model model) {

        Item item = itemService.detail(id);

        if (!item.getBrand().getId().equals(principal.getBrand().getId())) {
            return "redirect:/brand/list";
        }

        item.setIsExist(false);
        itemService.update(item);

        model.addAttribute("result", 1);
        return "brand/item/deleteOk";
    }
}
