package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.Item;
import com.lec.spring.domain.ItemAttachment;
import com.lec.spring.service.ItemAttachmentService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final UserService userService;

    public ItemController(ItemService itemService, ItemAttachmentService itemAttachmentService, UserService userService) {
        this.itemService = itemService;
        this.itemAttachmentService = itemAttachmentService;
        this.userService = userService;
    }

    // 전체 상품 목록 조회
    @GetMapping("/list")
    public String list(Model model) {

        // 전체 상품 리스트 조회
        List<Item> items = itemService.list();

        // 각 상품에 첫 번째 첨부파일을 설정
        for (Item item : items) {
            List<ItemAttachment> attachments = itemAttachmentService.findByItemId(item.getId());
            if (!attachments.isEmpty()) {
                item.setAttachment(attachments.get(0));
            }
        }

        model.addAttribute("items", items);
        return "item/list";
    }

    // 카데고리 별 상품 목록 조회
    @GetMapping("/category/{category}")
    public String listByCategory(@PathVariable String category, Model model) {
        List<Item> items = itemService.findByCategory(category);

        // 첨부파일 설정
        for (Item item : items) {
            List<ItemAttachment> attachments = itemAttachmentService.findByItemId(item.getId());
            if (!attachments.isEmpty()) {
                item.setAttachment(attachments.get(0));
            }
        }

        model.addAttribute("items", items);
        model.addAttribute("category", category);
        return "item/list";
    }

    // 상품 상세 정보 조회
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id,
                         @AuthenticationPrincipal PrincipalUserDetails principal,
                         Model model) {

        // 상품 정보 조회
        Item item = itemService.detail(id);

        // 첨부파일 조회 (첫 번째 첨부만 사용)
        List<ItemAttachment> attachments = itemAttachmentService.findByItemId(id);
        ItemAttachment attachment = !attachments.isEmpty() ? attachments.get(0) : null;

        // 모델에 상품과 첨부파일 정보 추가
        model.addAttribute("item", item);
        model.addAttribute("attachment", attachment);

        // 로그인 사용자 상태 정보 추가
        if (principal != null) {
            Long userId = principal.getUser().getId();
            String status = userService.findUserStatus(userId);
            String statusPlan = userService.findById(userId).getStatusPlan();

            model.addAttribute("accountStatus", status);
            model.addAttribute("statusPlan", statusPlan);
        } else {
            model.addAttribute("accountStatus", null);
            model.addAttribute("statusPlan", null);
        }
        return "item/detail";
    }
}