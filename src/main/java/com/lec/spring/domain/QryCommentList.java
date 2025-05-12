package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class QryCommentList extends QryResult{
	
	@ToString.Exclude
	@JsonProperty("data")  // JSON 변환시 "data" 란 이름의 property 로 변환됨.
	List<Comment> list;
	
}
