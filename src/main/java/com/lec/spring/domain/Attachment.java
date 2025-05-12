package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
	
	private Long id;
	private Long postId;
	private String sourcename;  // 원본 파일명
	private String filename;    // 저장된 파일명
	private Integer sequence;   // 이미지 순서
	
}
