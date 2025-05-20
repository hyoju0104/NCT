package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesByPlan {
	private List<String> months;                // ["2025-01", "2025-02", ...]
	private List<String> planNames;             // ["SILVER", "GOLD", "VIP"]
	private Map<String, List<Integer>> data;    //  key=planName, value=월별매출리스트
}
