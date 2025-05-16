package com.lec.spring.domain;

import com.lec.spring.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BrandItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            errors.rejectValue("name", "error.name", "상품명은 필수입니다.");
        }

        if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            errors.rejectValue("description", "error.description", "상품 설명은 필수입니다.");
        }

        if (item.getCategory() == null || item.getCategory().trim().isEmpty()) {
            errors.rejectValue("category", "error.category", "카테고리는 필수입니다.");
        }

        if (item.getStatusItem() == null) {
            errors.rejectValue("statusItem", "error.statusItem", "상품 상태는 필수입니다.");
        }
    }

}
