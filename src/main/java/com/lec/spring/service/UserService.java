package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;

import java.util.List;

public interface UserService {
    
    // 사용자의 user_id 로 회원정보 조회
    User findById(Long id);
    
    //사용자 아이디(username)로 회원정보 조회(로그인 시 사용)
    User findByUsername(String username);
    
    //아이디 중복 확인(회원가입 시 사용)
    boolean isExist(String username);
    
    //회원 가입 처리
    int register(User user);
    
    //사용자 ID로 권한 목록 조회(Spring Security에서 로그인 시 권한 부여용)
    List<Authority> selectAuthoritiesById(Long id);

    void updateUserInfo(User user);

    void refundPoint(Long userId, Integer amount);

    void createPayment(Long userId);

    void updateUserPlanId(Long id, Long planId);

    void markAsDeleted(Long userId);

    void inactivateUser(Long userId);

    String findUserStatus(Long userId);

}
