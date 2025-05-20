package com.lec.spring.service;

import com.lec.spring.domain.SalesByMonth;
import com.lec.spring.domain.SalesByPlan;

import java.util.List;
import java.util.Map;

public interface AdminSalesService {
	
	// 월별 매출 SELECT
	List<SalesByMonth> getSalesByMonth();
	
	// 구독별 매출 SELECT
	SalesByPlan getSalesByPlan();
	
}
