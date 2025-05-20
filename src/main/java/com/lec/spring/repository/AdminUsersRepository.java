package com.lec.spring.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminUsersRepository {
	
	// User.plan_id 기준 회원 수 집계
	List<Map<String, Object>> countUsersByPlan();
	
	// Rental.status 기준 회원 수 집계
	List<Map<String, Object>> countUsersRentalStatus();
	
	// Brand.id 별 대여 건수 집계
	List<Map<String, Object>> countRentalByBrand();
	
	// 연체 회원 목록 (user + overdue days + 상태)
	List<Map<String, Object>> findLateStatus();
	
}
