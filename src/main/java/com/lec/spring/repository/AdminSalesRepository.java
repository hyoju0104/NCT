package com.lec.spring.repository;

import com.lec.spring.domain.SalesByMonth;

import java.util.List;
import java.util.Map;

public interface AdminSalesRepository {
	
	// 월별 매출
	List<SalesByMonth> findSalesByMonth();
	
	// 구독별 매출
	List<Map<String, Object>> findSalesByPlan();
	
}
