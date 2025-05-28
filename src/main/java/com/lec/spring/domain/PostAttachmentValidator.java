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
	
	// 개별 파일 최대 크기(1000바이트)
	private static final long MAX_PER_FILE_BYTES = 1000;
	// 전체 첨부파일 최대 총합 (1000MB)
	private static final long MAX_TOTAL_BYTES = 1000L * 1024 * 1024; // 1000MB
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PostForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		PostForm form = (PostForm) target;
		List<MultipartFile> files = form.getFileList();
		
		System.out.println("✅ [Post] validate() 호출");
		
		// 1. 전체 첨부파일 용량 검증
		long totalSize = files.stream()
				.filter(f -> f != null && !f.isEmpty())
				.mapToLong(MultipartFile::getSize)
				.sum();
		if (totalSize > MAX_TOTAL_BYTES) {
			errors.rejectValue("fileList", "첨부파일 총 용량은 최대 1000MB 입니다.");
		}
		
		// 2. 개별 파일 타입 및 크기 검증
		for (MultipartFile file : files) {
			if (file == null || !file.isEmpty()
					|| file.getContentType() == null
					|| !IMAGE_MIME_TYPES.contains(file.getContentType())) {
				errors.rejectValue("fileList", "이미지 파일만 등록 가능합니다.");
				break;
			}
			
			if (file.getSize() > MAX_PER_FILE_BYTES) {
				errors.rejectValue("fileList", "파일 크기는 최대 1MB 입니다.");
				break;
			}
		}
	}
	
}
