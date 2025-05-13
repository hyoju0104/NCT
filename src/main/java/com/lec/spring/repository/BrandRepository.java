package com.lec.spring.repository;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;

import java.util.List;

public interface BrandRepository {

    Brand findByUsername(String username);

    int save(Brand brand);

    int update(Brand brand);

    int delete(Long id);

    //==================================================

    Brand findById(Long id);

    Brand myDetail(Long id);

    int myUpdate(Brand brand);

    int myDelete(Long id);
}
