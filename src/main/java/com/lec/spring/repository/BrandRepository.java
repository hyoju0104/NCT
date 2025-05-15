package com.lec.spring.repository;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;

import java.util.List;

public interface BrandRepository {

    Brand findByUsername(String username);

    int save(Brand brand);

    int delete(Long id);

    Brand findById(Long id);

    int update(Brand brand);

    int deactivate(Long id);
}
