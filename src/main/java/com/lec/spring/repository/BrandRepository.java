package com.lec.spring.repository;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;

public interface BrandRepository {

    Brand findById(Long id);

    Brand findByUsername(String username);

    int save(Brand brand);

    int update(Brand brand);

    int delete(Long id);
}
