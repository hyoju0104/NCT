package com.lec.spring.config;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;
import com.lec.spring.repository.BrandRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final BrandService brandService;
    
    public PrincipalDetailService(UserService userService, UserRepository userRepository, BrandRepository brandRepository, BrandService brandService) {
        this.userRepository = userRepository;
        this.userService = userService;
	    this.brandRepository = brandRepository;
        this.brandService = brandService;
    }

    
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loadUserByUsername : Spring Security가 로그인 시 자동으로 호출하는 함수
        // e.g. 로그인 폼에서 사용자가 hyoju0104 입력 시 loadUserByUsername("hyoju0104") 실행
        
        // (1) User 사용자 조회
        User user = userRepository.findByUsername(username);
        //해당 username의 user가 DB에 있다면 userDetails를 생성해서 리턴..
        if (user != null) {
            //PrincipalDetails : 사용자 정보 객체 > 그 안에 우리가 찾은 user 객체를 넣어서 리턴
            PrincipalUserDetails principalUserDetails = new PrincipalUserDetails(user, userService);
            return principalUserDetails;
        }
        
        // (2) Brand 사용자 조회
        Brand brand = brandRepository.findByUsername(username);
        if (brand != null) {
            PrincipalBrandDetails principalBrandDetails = new PrincipalBrandDetails(brand, brandService);
            return principalBrandDetails;
        }
        
        throw new UsernameNotFoundException(username); //해당 유저가 없다면.

    } //loadUserByUsername
}

/*
[ 사용자가 로그인 시도 ] → 아이디 입력
      ↓
Spring Security가 loadUserByUsername("아이디") 호출
      ↓
UserRepository.findByUsername(아이디)
      ↓
없으면 → 오류 던짐 (로그인 실패)
있으면 → PrincipalDetails(user) 만들어서 리턴 (로그인 성공 진행 가능)
*/