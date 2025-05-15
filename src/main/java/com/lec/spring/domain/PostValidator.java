package com.lec.spring.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("ℹ✅ supports(" + clazz.getName() + ") 호출");
		
		// ↓ 검증할 객체의 클래스 타입인지 확인 : Post = clazz; 가능 여부
		boolean result = Post.class.isAssignableFrom(clazz);
		System.out.println(result);
		return result;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post) target;
		
		System.out.println("✅ [Post] validate() 호출 : " + post);
		
		if (post.getContent() == null || post.getContent().trim().isEmpty()) {
			errors.rejectValue("content","내용은 반드시 입력해야 합니다.");
		}
		if (post.getItems() == null || post.getItems().trim().isEmpty()) {
			errors.rejectValue("items", "상품 목록은 반드시 입력해야 합니다.");
		}
	}
	
}
