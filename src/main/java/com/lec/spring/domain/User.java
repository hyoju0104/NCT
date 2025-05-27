package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	private Long id;
	private Long authId;
	private Long planId;
	
	private String username;
	@JsonIgnore // Json 으로 변환될 때 무시
	private String password;
	@ToString.Exclude   // toString() 메소드 생성 시 제외
	@JsonIgnore
	private String rePassword;
	
	private String name;
	private String phoneNum;
	private String email;
	private String zipcode;
	private String address;
	private String addressDetail;
	private Integer point;
	
	private String provider;
	private String providerId;
	private String statusPlan;
	
	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime signedAt;
	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime paidAt;

	private String statusAccount;
	
	private Integer rentalCnt;

	private Plan plan;

	public Plan getPlan() { return plan; }
	public void setPlan(Plan plan) { this.plan = plan; }


}
