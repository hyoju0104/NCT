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

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final BrandRepository brandRepository;

    @Override
    public int register(Brand brand) {
        brand.setUsername(brand.getUsername());
        brand.setPassword(passwordEncoder.encode(brand.getPassword()));

        brandRepository.save(brand);

        // 권한 부여
        Authority authority = authorityRepository.findByGrade("BRAND");
        if (authority != null) {
            authorityRepository.addAuthority(brand.getId(), authority.getId());
        }

        return 1;
    }

    @Override
    public boolean isExist(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public Brand findByUsername(String username) {
        System.out.println("brandRepository.findByUsername(username.toUpperCase()) : " + brandRepository.findByUsername(username.toUpperCase()));
        return brandRepository.findByUsername(username.toUpperCase());
    }

    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        Brand brand = brandRepository.findById(id);
        return authorityRepository.findByUser(brand);
    }

    @Override
    public Brand selectById(Long id) {
        Brand brand = brandRepository.findById(id);
        return brand;
    }

    @Override
    public Brand myDetail(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public int myUpdate(Brand brand) {
        return brandRepository.myUpdate(brand);
    }

    @Override
    public int myDelete(Long id) {
        return brandRepository.myDelete(id);
    }

}
