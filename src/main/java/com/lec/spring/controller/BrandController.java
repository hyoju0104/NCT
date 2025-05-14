package com.lec.spring.controller;

import com.lec.spring.config.BrandDetails;
import com.lec.spring.domain.Brand;
import com.lec.spring.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/mypage/detail")
    public String mypageDetail(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        Brand brand = brandService.myDetail(brandId);
        model.addAttribute("brand", brand);
        return "brand/mypage/detail";
    }

    @GetMapping("/mypage/update")
    public String myUpdate(@AuthenticationPrincipal BrandDetails principal, Model model) {
        Long brandId = principal.getBrand().getId();
        model.addAttribute("brand", brandService.selectById(brandId));
        return "brand/mypage/update";
    }

    @PostMapping("/mypage/update")
    public String myUpdateOk(
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @Valid Brand brand,
            BindingResult result,
            @AuthenticationPrincipal BrandDetails principal,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (result.hasErrors()) {
            showErrors(result);

            redirectAttributes.addFlashAttribute("phoneNum", brand.getPhoneNum());
            redirectAttributes.addFlashAttribute("description", brand.getDescription());

            for (FieldError err : result.getFieldErrors()) {
                redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/brand/mypage/update";
        }

        Long brandId = principal.getBrand().getId();
        brand.setId(brandId);

        int updated = brandService.myUpdate(brand, logo);
        model.addAttribute("result", updated);

        return "brand/mypage/updateOk";
    }

    @PostMapping("/mypage/delete")
    public String myDeleteOk(@AuthenticationPrincipal BrandDetails principal) {
        Long brandId = principal.getBrand().getId();
        brandService.myDelete(brandId);
        return "redirect:/login";
    }


    // ✅ 에러 디버깅 출력
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