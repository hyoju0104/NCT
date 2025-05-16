package com.lec.spring.domain;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PostAttachmentValidator implements Validator {
	
	private static final List<String> IMAGE_MIME_TYPES = Arrays.asList(
			"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"
	);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		MultipartFile file = (MultipartFile) target;
		
		System.out.println("✅ [Post] validate() 호출 : " + file);
		
		// 파일이 비었거나, 타입이 이미지가 아니면 에러
		if (file == null || file.isEmpty() || file.getContentType() == null || !IMAGE_MIME_TYPES.contains(file.getContentType())) {
			
			errors.rejectValue("fileList", "1개 이상의 이미지를 등록해주세요.");
		}
	}
	
}
