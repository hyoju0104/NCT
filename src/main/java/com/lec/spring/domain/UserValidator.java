package com.lec.spring.domain;

import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // 아이디 필수 + 중복 체크
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            errors.rejectValue("username", "아이디는 필수입니다");
        } else if (userService.isExist(user.getUsername())) {
            errors.rejectValue("username", "이미 존재하는 아이디입니다");
        }

        // 이름, 전화번호, 비밀번호 필수
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "이름은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNum", "전화번호는 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "비밀번호는 필수입니다");

        // 비밀번호 확인 일치 여부
        if (!user.getPassword().equals(user.getRePassword())) {
            errors.rejectValue("rePassword", "비밀번호가 일치하지 않습니다");
        }
    }
}

