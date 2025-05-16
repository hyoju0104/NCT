package com.lec.spring.controller;

import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.service.BrandAttachmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/attachment")
public class BrandAttachmentController {

    private final BrandAttachmentService brandAttachmentService;

    @Value("${app.upload.path.brand}")
    private String uploadDirBrand;

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
    @ResponseBody
    public String delete(@RequestParam("id") Long id) {
        BrandAttachment attachment = brandAttachmentService.findById(id);
        if (attachment != null) {
            File file = new File(uploadDirBrand + File.separator + attachment.getFilename());
            if (file.exists()) {
                file.delete();
            }
            brandAttachmentService.deleteById(id);
            return "삭제 성공";
        }
        return "삭제 실패";
    }
}
