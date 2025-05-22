package com.lec.spring.controller;

import com.lec.spring.domain.SalesByMonth;
import com.lec.spring.domain.SalesByPlan;
import com.lec.spring.service.AdminSalesService;
import com.lec.spring.service.AdminUsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminSalesService adminSalesService;
	private final AdminUsersService adminUsersService;
	
	public AdminController(AdminSalesService adminSalesService, AdminUsersService adminUsersService) {
		this.adminSalesService = adminSalesService;
		this.adminUsersService = adminUsersService;
	}
	
	
	// 1. 매출 관리
	@GetMapping("/sales")
	public String sales(Model model) {
		// 이번 달 총 매출
		long totalRevenue = adminSalesService.getTotalRevenueCurrentMonth();
		// 이번 달 전체 대여 건수
		long totalRentals = adminSalesService.getTotalRentalsCurrentMonth();
		// 전월 대비 매출 증감율 (%, 소수점 첫째 자리까지)
		double revenueChange = adminSalesService.getRevenueChangePercentMonthOnMonth();
		// 전월 대비 대여 건수 증감율
		double rentalChange = adminSalesService.getRentalChangePercentMonthOnMonth();
		
		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("totalRentals", totalRentals);
		model.addAttribute("revenueChange", revenueChange);
		model.addAttribute("rentalChange", rentalChange);
		
		return "admin/sales";
	}
	
	// 1-1) 기간별 매출 데이터(JSON)
	@GetMapping("/sales/monthly-data")
	@ResponseBody
	public List<SalesByMonth> salesMonthlyData(
			@RequestParam(value = "period", defaultValue = "month") String period
	) {
		switch (period) {
			case "quarter" :
				return adminSalesService.getSalesByQuarter();
			case "year" :
				return adminSalesService.getSalesByYear();
			default:
				return adminSalesService.getSalesByMonth();
		}
	}
	
	// 1-2) 구독 종류별 매출 데이터(JSON)
	@GetMapping("/sales/plan-data")
	@ResponseBody
	public SalesByPlan salesPlanData(
			@RequestParam(value = "period", defaultValue = "month") String period
	) {
		switch (period) {
			case "quarter" :
				return adminSalesService.getSalesByPlanQuarter();
			case "year" :
				return adminSalesService.getSalesByPlanYear();
			default:
				return adminSalesService.getSalesByPlanMonth();
		}
	}
	
	
	// 2. 회원 관리
	@GetMapping("/users")
	public String users(Model model) {
		return "admin/users";
	}
	
	// 2-1) 구독 종류별 회원수 pie chart 출력
	@GetMapping("/users/plan-data")
	@ResponseBody
	public List<Map<String, Object>> usersPlanData() {
		return adminUsersService.getUsersByPlan();
	}
	
	// 2-2) 대여 상태별 회원 수
	@GetMapping("/users/status-data")
	@ResponseBody
	public List<Map<String, Object>> usersStatusData() {
		return adminUsersService.getUsersByStatus();
	}
	
	// 2-3) 브랜드별 대여 건수
	@GetMapping("/users/brand-data")
	@ResponseBody
	public List<Map<String, Object>> usersBrandData() {
		return adminUsersService.getRentalsByBrand();
	}
	
	// 2-4) 정지된 회원 관리
	@GetMapping("/users/late-data")
	@ResponseBody
	public List<Map<String, Object>> usersLateData() {
		return adminUsersService.getInactiveUsers();
	}
	
	// 2-5) 정지 해제
	@PostMapping("/users/{userId}/release")
	@ResponseBody
	public void release(@PathVariable Long userId) {
		adminUsersService.releaseSuspension(userId);
	}
	
}
