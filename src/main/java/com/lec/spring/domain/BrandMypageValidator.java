package com.lec.spring.domain;

import com.lec.spring.domain.Brand;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BrandMypageValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Brand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Brand brand = (Brand) target;

        if (brand.getPhoneNum() == null || brand.getPhoneNum().trim().isEmpty()) {
            errors.rejectValue("phoneNum", null, "대표번호는 필수입니다.");
        }
    }

    public void validatePasswords(String password, String password2, Errors errors) {
        if (password != null && !password.trim().isEmpty()) {
            if (!password.equals(password2)) {
                errors.rejectValue("password", null, "비밀번호가 일치하지 않습니다.");
            }
        }
    }
}
