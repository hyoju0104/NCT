package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.Brand;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.BrandRepository;
import com.lec.spring.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {

    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final BrandRepository brandRepository;
    private final ItemRepository itemRepository;

    public BrandServiceImpl(PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, SqlSession sqlSession, ItemRepository itemRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.brandRepository = sqlSession.getMapper(BrandRepository.class);
        this.itemRepository = itemRepository;
    }

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
        System.out.println("brandRepository.findByUsername(username) : " + brandRepository.findByUsername(username));
        return brandRepository.findByUsername(username);
    }

    @Override
    public List<Authority> selectAuthoritiesById(Long id) {
        Brand brand = brandRepository.findById(id);
        return authorityRepository.findByUser(brand);
    }

    @Override
    public Brand selectById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand myDetail(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public int myUpdate(Brand brand) {
        return brandRepository.update(brand);
    }

    @Override
    public int myDelete(Long id) {
        itemRepository.markAllItemsAsNotExistByBrandId(id);
        return brandRepository.deactivate(id); // is_actived = false 처리
    }

}
