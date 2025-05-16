package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping("/admin/sales")
	public String sales(Model model) {
		// TODO: 매출 데이터 조회해서 model.addAttribute("salesData", ...)
		return "admin/sales";  // templates/admin/sales.html 템플릿을 렌더링
	}
	
	@GetMapping("/admin/users")
	public String users(Model model) {
		// TODO: 매출 데이터 조회해서 model.addAttribute("salesData", ...)
		return "admin/users";  // templates/admin/sales.html 템플릿을 렌더링
	}
	
}
