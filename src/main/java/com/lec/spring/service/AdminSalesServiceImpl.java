package com.lec.spring.service;

import com.lec.spring.domain.SalesByMonth;
import com.lec.spring.domain.SalesByPlan;
import com.lec.spring.repository.AdminSalesRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminSalesServiceImpl implements AdminSalesService {
	
	private final AdminSalesRepository adminSalesRepository;
	
	public AdminSalesServiceImpl(SqlSession sqlSession) {
		this.adminSalesRepository = sqlSession.getMapper(AdminSalesRepository.class);
	}
	
	
	@Override
	public List<SalesByMonth> getSalesByMonth() {
		return adminSalesRepository.findSalesByMonth();
	}
	
	@Override
	public SalesByPlan getSalesByPlan() {
		List<Map<String, Object>> raw = adminSalesRepository.findSalesByPlan();
		
		// 0. Null 필터
		List<Map<String, Object>> safeRaw = raw.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		
		// 1. 전체 month 목록 추출
		List<String> months = raw.stream()
				.map(m -> (String) m.get("month"))
				.distinct()
				.sorted()
				.collect(Collectors.toList());
		
		// 2. planNames 목록
		List<String> planNames = raw.stream()
				.map(m -> (String) m.get("planName"))
				.distinct()
				.collect(Collectors.toList());
		
		// 3. 데이터 구조화
		Map<String, List<Integer>> data = new HashMap<>();
		for (String plan : planNames) {
			data.put(plan, new ArrayList<>(Collections.nCopies(months.size(), 0)));
		}
		for (Map<String, Object> row : raw) {
			String plan = (String) row.get("planName");
			String month = (String) row.get("month");
			Integer total = ((Number) row.get("total")).intValue();
			int idx = months.indexOf(month);
			data.get(plan).set(idx, total);
		}
		
		// 4. 객체 생성
		SalesByPlan salesByPlan = new SalesByPlan();
		salesByPlan.setMonths(months);
		salesByPlan.setPlanNames(planNames);
		salesByPlan.setData(data);
		
		return salesByPlan;
	}
	
}
