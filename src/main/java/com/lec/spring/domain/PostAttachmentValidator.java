package com.lec.spring.domain;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PostAttachmentValidator implements Validator {
	
	// 지원하는 이미지 MIME 타입
	private static final List<String> IMAGE_MIME_TYPES = Arrays.asList(
			"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"
	);
	
	// 전체 첨부파일 최대 총합 (1000MB)
	private static final long MAX_TOTAL_BYTES = 1000L * 1024 * 1024; // 1000MB
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		MultipartFile file = (MultipartFile) target;
		
		System.out.println("✅ [Post] validate() 호출");
		
		String type = file.getContentType();
		if (!IMAGE_MIME_TYPES.contains(type)) {
			errors.rejectValue("fileList", "이미지 파일만 업로드 가능합니다");
		}
	}
	
}
