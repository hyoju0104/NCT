package com.lec.spring.repository;

import com.lec.spring.domain.Authority;

public interface AuthorityRepository {

    Authority findById(Long id);
}
