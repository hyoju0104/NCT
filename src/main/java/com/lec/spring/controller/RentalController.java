package com.lec.spring.controller;

import com.lec.spring.domain.Rental;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brand/rental")
public class RentalController {

    private final RentalService rentalService;
    private UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String brandRentalList(Model model, HttpSession session) {
        Long brandId = (Long) session.getAttribute("brandId");

        rentalService.updateOverdueStatus();
        List<Rental> rentals = rentalService.findRentalsByBrandId(brandId);

        model.addAttribute("rentals", rentals);
        return "brand/rental/list";
    }

    @PostMapping("/return/{id}")
    public String returnItem(@PathVariable("id") Long rentalId) {
        rentalService.updateReturned(rentalId);
        return "redirect:/brand/rental/list";
    }

    @PostMapping("/deactivate/{userId}")
    public String inactiveUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.inactivateUser(userId);  // status_account = 'INACTIVE'
        redirectAttributes.addFlashAttribute("msg", "계정이 정지되었습니다.");
        return "redirect:/brand/rental/list";
    }


}