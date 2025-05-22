package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {

    // 브랜드 회원가입
    int register(Brand brand);

    // username 중복 확인
    boolean isExist(String username);

    // username 조회
    Brand findByUsername(String username);

    // 권한 조회
    List<Authority> selectAuthoritiesById(Long id);

    // 정보 조회
    Brand myDetail(Long id);

    // id 조회
    Brand selectById(Long id);

    // 정보 수정
    int myUpdate(Brand brand);

    // 계정 탈퇴
    int myDelete(Long id);

}