package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesByMonth { // 월 매출 관련 DTO
	private String month;
	private Integer total; // 해당 월 매출 합계
}
