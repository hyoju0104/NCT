package com.lec.spring.controller;

import com.lec.spring.domain.Brand;
import com.lec.spring.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/mypage/detail/{id}")
    public String myDetail(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.myDetail(id));
        return "brand/mypage/detail";
    }

}

