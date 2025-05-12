package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Brand;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

//    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final BrandRepository brandRepository;

    @Override
    public int register(Brand brand) {
//        brand.setUsername(brand.getUsername().toUpperCase());
//        brand.setPassword(passwordEncoder.encode(brand.getPassword()));
//
//        brandRepository.save(brand);
//
//        Authority authority = authorityRepository.findByGrade("ROLE_BRAND");
//
//        Long brandId = brand.getId();
//        Long authorityId = authority.getId();
//
//        authorityRepository.addAuthority(authorityId, brandId);

        return 1;
    }

    @Override
    public boolean isExist(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public Brand findByUsername(String username) {
        return brandRepository.findByUsername(username.toUpperCase());
    }

    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        Brand brand = brandRepository.findById(id);
        return authorityRepository.findByUser(brand);
    }

    @Override
    public Brand myDetail(Long id) {
        return brandRepository.myDetail(id);
    }

    @Override
    public int myUpdate(Brand brand, Long id) {
        return brandRepository.myUpdate(brand);
    }

    @Override
    public int myDelete(Brand brand, Long id) {
        return brandRepository.myDelete(brand);
    }

}
