package com.lec.spring.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	
	private Long id;
	private String content;
	private LocalDateTime createdAt;
	private String items;
	
	private User user;
	
	// 첨부파일 정보
//	@ToString.Exclude   // List 객체는 ToString 필요 X (주소값 반환)
//	@Builder.Default    // 초기값이 주어진 경우, builder 제공 X
//	private List<Attachment> fileList = new ArrayList<>()
	
}
