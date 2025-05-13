package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Brand;

import java.util.List;

public interface BrandService {

    int register(Brand brand);

    boolean isExist(String username);

    Brand findByUsername(String username);

    List<Authority> selectAuthoritiesById(Long id);


    Brand myDetail(Long id);

    Brand selectById(Long id);

    int myUpdate(Brand brand);

    int myDelete(Long id);

}