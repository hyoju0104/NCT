package com.lec.spring.controller;

import com.lec.spring.domain.Rental;
import com.lec.spring.service.RentalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }
    // [USER] 내 대여 목록 보기
    @GetMapping("/user/mypage/rentals")
    public String userRentalList(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        List<Rental> rentals = rentalService.getRentalsByUserId(userId);
        rentalService.updateOverdue();
        model.addAttribute("rentals", rentals);
        return "user/mypage/rentals";
    }

    @GetMapping("/brand/rentals")
    public String brandRentalList(Model model, HttpSession session) {
        Long brandId = (Long) session.getAttribute("brandId");
        List<Rental> rentals = rentalService.getRentalsByBrandId(brandId);
        model.addAttribute("rentals", rentals);
        return "brand/rentals/list";
    }

    @PostMapping("/brand/rentals/return/{id}")
    public String returnItem(@PathVariable("id") Long rentalId) {
        rentalService.updateReturned(rentalId);
        return "redirect:/rental/brand/rentals";
    }
}
