package com.lec.spring.controller;

import com.lec.spring.domain.Rental;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.ItemService;
import com.lec.spring.service.RentalService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brand/rental")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;
    private final UserValidator userValidator;

    public RentalController(RentalService rentalService, UserService userService, UserValidator userValidator) {
        this.rentalService = rentalService;
        this.userService = userService;
	    this.userValidator = userValidator;
    }
    
    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    // 브랜드가 등록한 상품에 대한 대여 리스트 조회
    // 연체 상태 자동 갱신
    @GetMapping("/list")
    public String brandRentalList(Model model, HttpSession session) {
        Long brandId = (Long) session.getAttribute("brandId");

        // 연체 자동 갱신
        rentalService.updateOverdueStatus();

        // 대여 리스트 조회
        List<Rental> rentals = rentalService.findRentalsByBrandId(brandId);

        model.addAttribute("rentals", rentals);
        return "brand/rental/list";
    }

    // 대여 상품 반납 처리
    @PostMapping("/return/{id}")
    public String returnItem(@PathVariable("id") Long rentalId) {
        rentalService.updateReturned(rentalId);
        return "redirect:/brand/rental/list";
    }

    // 특정 사용자 계정 비활성화 처리
    @PostMapping("/deactivate/{userId}")
    public String inactiveUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.inactivateUser(userId);  // status_account = 'INACTIVE'
        redirectAttributes.addFlashAttribute("msg", "계정이 정지되었습니다.");
        return "redirect:/brand/rental/list";
    }
    
}