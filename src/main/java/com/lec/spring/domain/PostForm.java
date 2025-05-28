package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 게시글 등록 폼 데이터를 담는 DTO
 * - 첨부파일 리스트(fileList) 보유
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostForm {
	private List<MultipartFile> fileList;
}
