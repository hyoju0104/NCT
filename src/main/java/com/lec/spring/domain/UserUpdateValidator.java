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

        // 전화번호 검증: 숫자와 하이픈(-)만 허용
        if (user.getPhoneNum() == null || user.getPhoneNum().trim().isEmpty()) {
            errors.rejectValue("phoneNum", null, "전화번호는 필수입니다.");
        } else {
            String phone = user.getPhoneNum().trim();
            if (!phone.matches("[0-9\\-]+")) {
                errors.rejectValue("phoneNum", null, "전화번호는 숫자와 하이픈(-)만 입력 가능합니다.");
            }
        }
    }
}
