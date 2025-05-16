package com.lec.spring.controller;

import com.lec.spring.config.BrandDetails;
import com.lec.spring.domain.Brand;
import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.domain.BrandMypageValidator;
import com.lec.spring.service.BrandAttachmentService;
import com.lec.spring.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;
    private final PasswordEncoder passwordEncoder;
    private final BrandAttachmentService brandAttachmentService;

    @Value("${app.upload.path.brand}")
    private String uploadDirBrand;

    public BrandController(BrandService brandService, PasswordEncoder passwordEncoder, BrandAttachmentService brandAttachmentService) {
        this.brandService = brandService;
        this.passwordEncoder = passwordEncoder;
        this.brandAttachmentService = brandAttachmentService;
    }

    @GetMapping("/mypage/detail")
    public String mypageDetail(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        Brand brand = brandService.myDetail(brandId);
        model.addAttribute("brand", brand);

        List<BrandAttachment> attachments = brandAttachmentService.findByBrandId(brandId);
        model.addAttribute("attachments", attachments);

        return "brand/mypage/detail";
    }

    @GetMapping("/mypage/update")
    public String myUpdate(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        model.addAttribute("brand", brandService.selectById(brandId));

        List<BrandAttachment> attachments = brandAttachmentService.findByBrandId(brandId);
        model.addAttribute("attachments", attachments);

        return "brand/mypage/update";
    }

    @PostMapping("/mypage/update")
    public String myUpdateOk(
            @RequestParam(required = false) MultipartFile[] files,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String password2,
            @Valid Brand brand,
            BindingResult result,
            @AuthenticationPrincipal BrandDetails principal,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        BrandMypageValidator validator = new BrandMypageValidator();
        validator.validatePasswords(password, password2, result);
        validator.validate(brand, result);

        if (result.hasErrors()) {
            showErrors(result);
            redirectAttributes.addFlashAttribute("phoneNum", brand.getPhoneNum());
            redirectAttributes.addFlashAttribute("description", brand.getDescription());
            redirectAttributes.addFlashAttribute("passwordFields", password != null && !password.isBlank());
            for (FieldError err : result.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getDefaultMessage());
            }
            return "redirect:/brand/mypage/update";
        }

        Long brandId = principal.getBrand().getId();
        brand.setId(brandId);

        if (password != null && !password.isBlank()) {
            brand.setPassword(passwordEncoder.encode(password));
        } else {
            Brand current = brandService.selectById(principal.getBrand().getId());
            brand.setPassword(current.getPassword());
        }

        brandService.myUpdate(brand);

        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDirBrand);
                        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

                        String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                        Path filePath = uploadPath.resolve(savedName);

                        file.transferTo(filePath.toFile());

                        BrandAttachment attachment = BrandAttachment.builder()
                                .brandId(brandId)
                                .sourcename(file.getOriginalFilename())
                                .filename(savedName)
                                .build();

                        brandAttachmentService.save(attachment);

                    } catch (IOException e) {
                        redirectAttributes.addFlashAttribute("error", "파일 업로드에 실패했습니다.");
                    }
                }
            }
        }


        int resultUpdate = brandService.myUpdate(brand);
        model.addAttribute("result", resultUpdate);
        model.addAttribute("brand", brand);

        return "brand/mypage/updateOk";
    }

    @PostMapping("/mypage/delete")
    public String myDeleteOk(@AuthenticationPrincipal BrandDetails principal) {
        Long brandId = principal.getBrand().getId();
        brandService.myDelete(brandId);
        return "brand/mypage/deleteOk";
    }

    public void showErrors(Errors errors) {
        if (errors.hasErrors()) {
            System.out.println("💢에러개수: " + errors.getErrorCount());
            System.out.println("\t[field]\t|[code]");
            for (FieldError err : errors.getFieldErrors()) {
                System.out.println("\t" + err.getField() + "\t|" + err.getCode());
            }
        } else {
            System.out.println("✔에러 없슴");
        }
    }
}