package com.lec.spring.controller;

import com.lec.spring.domain.Rental;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand/rental")
public class RentalController {

    private final RentalService rentalService;
    private final ItemService itemService;

    public RentalController(RentalService rentalService, ItemService itemService) {
        this.rentalService = rentalService;
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String brandRentalList(Model model, HttpSession session) {
        Long brandId = (Long) session.getAttribute("brandId");
        System.out.println("세션 brandId = " + brandId);

        List<Rental> rentals = rentalService.getRentalsByBrandId(brandId);
//        System.out.println("렌탈 수 = " + rentals.size());
//        for (Rental r : rentals) {
//            System.out.println("렌탈 ID = " + r.getId() + ", 아이템명 = " + r.getItem().getName());
//        }

        model.addAttribute("rentals", rentals);
        return "brand/rental/list";
    }

    @PostMapping("/return/{id}")
    public String returnItem(@PathVariable("id") Long rentalId) {
        rentalService.updateReturned(rentalId);
        return "redirect:/brand/rental/list";
    }
}
