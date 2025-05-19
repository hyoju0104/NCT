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



}
