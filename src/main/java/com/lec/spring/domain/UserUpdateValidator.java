package com.lec.spring.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            errors.rejectValue("name", null, "이름은 필수입니다.");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", null, "비밀번호는 필수입니다.");
        }

        if (user.getRePassword() == null || user.getRePassword().trim().isEmpty()) {
            errors.rejectValue("rePassword", null, "비밀번호 확인은 필수입니다.");
        } else if (!user.getPassword().equals(user.getRePassword())) {
            errors.rejectValue("rePassword", null, "비밀번호가 일치하지 않습니다.");
        }
        
        // 전화번호 : 값이 비어있으면 통과, 비어있지 않으면 숫자와 하이픈(-)만 가능
        String phone = user.getPhoneNum();
        if (phone != null && !phone.isBlank()) {
            String trimmedPhone = phone.trim();
            if (!trimmedPhone.matches("[0-9\\-]+")) {
                errors.rejectValue("phoneNum", null, "전화번호는 숫자와 하이픈(-)만 입력 가능합니다.");
            }
        }
    }
}
