package com.lec.spring.repository;

import com.lec.spring.domain.Rental;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalRepository {
    // 특정 사용자 ID로 대여 내역 전부 가져오기
    List<Rental> findAllByUserId(Long userId);
}

