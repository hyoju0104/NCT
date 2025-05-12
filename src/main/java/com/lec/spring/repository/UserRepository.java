package com.lec.spring.repository;

import com.lec.spring.domain.User;

public interface UserRepository {

    User findByUsername(String username);
    int save(User user); //사용자 등록

    User findById(Long id);
}
