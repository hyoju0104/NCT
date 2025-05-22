package com.lec.spring.service;

import com.lec.spring.domain.SalesByMonth;
import com.lec.spring.domain.SalesByPlan;

import java.util.List;
import java.util.Map;

public interface AdminSalesService {
	
	// 월간 전체 매출 SELECT
	List<SalesByMonth> getSalesByMonth();
	
	// 분기 전체 매출 SELECT
	List<SalesByMonth> getSalesByQuarter();
	
	// 연간 전체 매출 SELECT
	List<SalesByMonth> getSalesByYear();
	
	// 구독별 월 매출 SELECT
	SalesByPlan getSalesByPlanMonth();
	
	// 구독별 분기 매출 SELECT
	SalesByPlan getSalesByPlanQuarter();
	
	// 구독별 연 매출 SELECT
	SalesByPlan getSalesByPlanYear();
	
	// 이번 달 총 매출
	long getTotalRevenueCurrentMonth();
	
	// 이번 달 전체 대여 건수
	long getTotalRentalsCurrentMonth();
	
	// 전월 대비 매출 증감율 (%, 소수점 첫째 자리까지)
	double getRevenueChangePercentMonthOnMonth();
	
	// 전월 대비 대여 건수 증감율
	double getRentalChangePercentMonthOnMonth();
	
}
