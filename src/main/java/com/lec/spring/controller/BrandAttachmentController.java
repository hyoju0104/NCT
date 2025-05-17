package com.lec.spring.controller;

import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.service.BrandAttachmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/brand/attachment")
public class BrandAttachmentController {

    @Value("${app.upload.path.brand}")
    private String uploadDirBrand;

    private final BrandAttachmentService brandAttachmentService;

    public BrandAttachmentController(BrandAttachmentService brandAttachmentService) {
        this.brandAttachmentService = brandAttachmentService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("brandId") Long brandId,
                         @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "파일이 없습니다.";
        }

        try {
            Path uploadPath = Paths.get(uploadDirBrand);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(savedName);
            file.transferTo(filePath.toFile());

            BrandAttachment attachment = new BrandAttachment();
            attachment.setBrandId(brandId);
            attachment.setSourcename(file.getOriginalFilename());
            attachment.setFilename(savedName);

            brandAttachmentService.save(attachment);
            return "업로드 성공";

        } catch (IOException e) {
            e.printStackTrace();
            return "업로드 실패: " + e.getMessage();
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        BrandAttachment attachment = brandAttachmentService.findById(id);
        if (attachment != null) {
            File file = new File(uploadDirBrand + File.separator + attachment.getFilename());
            if (file.exists()) {
                file.delete();
            }
            brandAttachmentService.deleteById(id);
            redirectAttributes.addFlashAttribute("msg", "첨부파일이 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("msg", "삭제할 파일을 찾을 수 없습니다.");
        }

        return "redirect:/brand/mypage/update";
    }

}
