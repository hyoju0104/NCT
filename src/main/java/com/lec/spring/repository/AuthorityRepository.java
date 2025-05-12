package com.lec.spring.repository;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;

import java.util.List;

public interface AuthorityRepository {

    Authority findByGrade(String grade);
    void addAuthority(Long userId, Long authId); //권한부여메서드
    // 특정 사용자(User) 의 권한(들) 읽어오기
    List<Authority> findByUser(User user);
    List<Authority> findByUser(Brand brand);
}
