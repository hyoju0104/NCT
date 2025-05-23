package com.lec.spring.repository;

import com.lec.spring.domain.SalesByMonth;

import java.util.List;
import java.util.Map;

public interface AdminSalesRepository {
	
	// 월별 전체 매출
	List<SalesByMonth> findSalesByMonth();
	
	// 분기별 전체 매출
	List<SalesByMonth> findSalesByQuarter();
	
	// 연간 전체 매출
	List<SalesByMonth> findSalesByYear();
	
	// 구독별 월 매출
	List<Map<String, Object>> findSalesByPlanMonth();
	
	// 구독별 분기 매출
	List<Map<String, Object>> findSalesByPlanQuarter();
	
	// 구독별 연 매출
	List<Map<String, Object>> findSalesByPlanYear();
	
	
	// 지정 연·월의 총 매출 합계 조회
	Long findTotalRevenue(int year, int month);
	
	// 지정 연·월의 대여 건수 조회
	Long findTotalRentals(int year, int month);
	
}
