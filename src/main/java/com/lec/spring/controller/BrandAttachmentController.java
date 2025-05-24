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

    // 브랜드 첨부파일 업로드 요청
    @PostMapping("/upload")
    @ResponseBody   // 결과 문자열을 HTTP 응답 본문으로 바로 반환
    public String upload(@RequestParam("brandId") Long brandId,
                         @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "파일이 없습니다.";
        }

        try {
            // 업로드 폴더가 존재하지 않으면 생성
            Path uploadPath = Paths.get(uploadDirBrand);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일 이름에 UUID를 붙여 저장명 생성
            String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(savedName);

            // 파일 저장 (Spring 제공 메서드 사용)
            file.transferTo(filePath.toFile());

            // DB에 파일 정보 저장
            BrandAttachment attachment = new BrandAttachment();
            attachment.setBrandId(brandId);
            attachment.setSourcename(file.getOriginalFilename());
            attachment.setFilename(savedName);

            brandAttachmentService.save(attachment);
            return "업로드 성공";

        } catch (IOException e) {
            return "업로드 실패: " + e.getMessage();
        }
    }

    // 첨부파일 삭제 요청을 처리
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        BrandAttachment attachment = brandAttachmentService.findById(id);

        // 파일이 존재하는 경우에만 삭제 처리
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
