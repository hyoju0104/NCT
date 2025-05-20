package com.lec.spring.service;

import java.util.List;
import java.util.Map;

public interface AdminUsersService {
	
	// 구독 종류별 사용자 분포 SELECT
	List<Map<String, Object>> getUsersByPlan();
	
	// 대여 상태별 건수 SELECT
	List<Map<String, Object>> getUsersByStatus();
	
	// 브랜드별 대여 건수 SELECT
	List<Map<String, Object>> getRentalsByBrand();
	
	// 연체된 USER 회원 리스트 SELECT
	List<Map<String, Object>> getInactiveUsers();
	
	// status_account 를 ACTIVE 로 UPDATE
	void releaseSuspension(Long userId);
	
}
