package com.lec.spring.domain;

public class Plan {
	
	private Long id;
	private Type type;
	private Integer price;
	private Integer count;
	
	public enum Type { SILVER, GOLD, VIP }
	
}
