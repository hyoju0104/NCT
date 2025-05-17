package com.lec.spring.controller;

import com.lec.spring.domain.Item;
import com.lec.spring.domain.ItemAttachment;
import com.lec.spring.service.ItemAttachmentService;
import com.lec.spring.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;

@Controller
@RequestMapping("/item/attachment")
public class ItemAttachmentController {

    @Value("${app.upload.path.item}")
    private String uploadDirItem;

    private final ItemService itemService;
    private final ItemAttachmentService itemAttachmentService;

    public ItemAttachmentController(ItemService itemService, ItemAttachmentService itemAttachmentService) {
        this.itemService = itemService;
        this.itemAttachmentService = itemAttachmentService;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        ItemAttachment attachment = itemAttachmentService.findById(id);

        if (attachment != null) {
            File file = new File(System.getProperty("user.dir") + "/" + uploadDirItem + "/" + attachment.getFilename());
            if (file.exists()) file.delete();

            itemAttachmentService.deleteById(id);

            Item item = itemService.detail(attachment.getItemId());
            redirectAttributes.addFlashAttribute("item", item);
            redirectAttributes.addFlashAttribute("attachment", null);
            redirectAttributes.addFlashAttribute("msg", "첨부파일이 삭제되었습니다.");

            return "redirect:/brand/item/update/" + attachment.getItemId();
        }

        redirectAttributes.addFlashAttribute("msg", "삭제할 파일을 찾을 수 없습니다.");
        return "redirect:/brand/list";
    }


}
