package com.lec.spring.repository;

import com.lec.spring.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserRepository {

    User findByUsername(String username);
    int save(User user); //사용자 등록

    User findById(Long id);

    void updateUserInfo(User user);

    // 포인트 차감
    int refundPoint(@Param("id") Long id, @Param("amount") Integer amount);

    int updateUserPlanId(@Param("id") Long userId, @Param("planId") Long planId);

    int updatePaidAt(Long id);

    //글 작성 시 포인트 지급
    // 포인트 증가
    int addPoint(@Param("id") Long id, @Param("amount") Integer amount);

    //빌린 횟수 증가 메서드
    int increaseRentalCount(Long userId);

    int updateStatusToDeleted(Long userId);

    // 회원 상태(status_account) 업데이트
    void updateStatusAccount(@Param("userId") Long userId, @Param("status") String status);

    String findStatusAccountById(Long id);

    // 주소 업데이트
    void updateUserAddress(User user);

    void updateStatusPlan(@Param("id") Long id, @Param("planId") Long planId);

    // 연락처 업데이트
    void updateUserPhoneNum(User user);

}
