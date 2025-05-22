package com.lec.spring.repository;

import com.lec.spring.domain.SalesByMonth;

import java.util.List;
import java.util.Map;

public interface AdminSalesRepository {
	
	// 월별 매출
	List<SalesByMonth> findSalesByMonth();
	
	// 구독별 매출
	List<Map<String, Object>> findSalesByPlan();
	
	// 지정 연·월의 총 매출 합계 조회
	Long findTotalRevenue(int year, int month);
	
	// 지정 연·월의 대여 건수 조회
	Long findTotalRentals(int year, int month);
	
}
