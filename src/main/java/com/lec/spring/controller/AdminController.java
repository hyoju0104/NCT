package com.lec.spring.controller;

import com.lec.spring.domain.SalesByMonth;
import com.lec.spring.domain.SalesByPlan;
import com.lec.spring.service.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final SalesService salesService;
	
	public AdminController(SalesService salesService) {
		this.salesService = salesService;
	}
	
	
	@GetMapping("/sales")
	public String sales(Model model) {
		// TODO: 매출 데이터 조회해서 model.addAttribute("salesData", ...)
		return "admin/sales";  // templates/admin/sales.html 템플릿을 렌더링
	}
	
	// 2) 월별 매출 데이터(JSON)
	@GetMapping("/sales/monthly-data")
	@ResponseBody
	public List<SalesByMonth> monthlyData() {
		return salesService.getSalesByMonth();
	}
	
	// 3) 구독 종류별 매출 데이터(JSON)
	@GetMapping("/sales/plan-data")
	@ResponseBody
	public SalesByPlan planData() {
		return salesService.getSalesByPlan();
	}
	
	
	@GetMapping("/users")
	public String users(Model model) {
		// TODO: 매출 데이터 조회해서 model.addAttribute("salesData", ...)
		return "admin/users";  // templates/admin/sales.html 템플릿을 렌더링
	}
	
}
