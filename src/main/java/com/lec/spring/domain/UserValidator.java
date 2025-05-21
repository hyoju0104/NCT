package com.lec.spring.domain;

import com.lec.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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
            errors.rejectValue("username", null, "아이디는 필수입니다.");
        } else if (userService.isExist(user.getUsername())) {
            errors.rejectValue("username", null, "이미 존재하는 아이디입니다.");
        }

        // 이름
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            errors.rejectValue("name", null, "이름은 필수입니다.");
        }
        
        // 비밀번호
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", null, "비밀번호는 필수입니다.");
        }

        // 비밀번호 확인
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
