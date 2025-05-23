package com.lec.spring.repository;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;

import java.util.List;

public interface BrandRepository {

    // 브랜드 username 조회
    Brand findByUsername(String username);

    // Brand 정보 저장
    int save(Brand brand);

    //
    int delete(Long id);

    // 브랜드 id 조회
    Brand findById(Long id);

    // 정보 수정
    int update(Brand brand);

    // is_actived true -> false
    int deactivate(Long id);
}
