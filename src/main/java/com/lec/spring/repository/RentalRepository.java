package com.lec.spring.repository;

import com.lec.spring.domain.Rental;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalRepository {

    // 대여 등록
    int save(Rental rental);

    // 단건 조회
    Rental findById(Long id);

    // 유저 마이페이지: 내가 대여한 목록
    List<Rental> findAllByUserId(Long userId);

    // 브랜드 계정: 내가 등록한 아이템의 대여 목록
    List<Rental> findAllByBrandId(Long brandId);

    // 반납 처리 (returned_at + 상태 변경)
    int updateReturned(Long id);

    // 연체 상태 자동 갱신
    int updateOverdue();

    // 탈퇴시 대여중 있는지 검사할때 필요
    int countActiveRentalsByUserId(Long userId);

    // 배송완료 처리
    int updateDelivered(Long rentalId);
}
