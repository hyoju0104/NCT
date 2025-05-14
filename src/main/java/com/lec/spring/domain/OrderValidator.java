package com.lec.spring.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);  // User 객체 검증
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getPhoneNum() == null || user.getPhoneNum().trim().isEmpty()) {
            errors.rejectValue("phoneNum", null, "전화번호는 필수입니다.");
        }

        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            errors.rejectValue("address", null, "주소지는 필수입니다.");
        }
    }
}