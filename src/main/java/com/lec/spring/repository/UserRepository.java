package com.lec.spring.repository;

import com.lec.spring.domain.User;

public interface UserRepository {

    User findByUsername(String username);
}
