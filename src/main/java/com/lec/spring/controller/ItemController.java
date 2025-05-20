package com.lec.spring.controller;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.ItemAttachment;
import com.lec.spring.service.ItemAttachmentService;
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
    private final ItemAttachmentService itemAttachmentService;

    public ItemController(ItemService itemService, ItemAttachmentService itemAttachmentService) {
        this.itemService = itemService;
        this.itemAttachmentService = itemAttachmentService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> items = itemService.list();

        for (Item item : items) {
            List<ItemAttachment> attachments = itemAttachmentService.findByItemId(item.getId());
            if (!attachments.isEmpty()) {
                item.setAttachment(attachments.get(0));
            }
        }

        model.addAttribute("items", items);
        return "item/list";
    }


    @GetMapping("/category/{category}")
    public String listByCategory(@PathVariable String category, Model model) {
        model.addAttribute("items", itemService.findByCategory(category));
        return "item/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Item item = itemService.detail(id);

        List<ItemAttachment> attachments = itemAttachmentService.findByItemId(id);
        ItemAttachment attachment = !attachments.isEmpty() ? attachments.get(0) : null;

        model.addAttribute("item", item);
        model.addAttribute("attachment", attachment);
        return "item/detail";
    }
}