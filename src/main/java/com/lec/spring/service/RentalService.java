package com.lec.spring.service;
import com.lec.spring.domain.Rental;
import java.util.List;

public interface RentalService {

    // 유저의 대여 목록
    List<Rental> findAllByUserId(Long userId);

    // 브랜드 등록 아이템 대여 목록
    List<Rental> findRentalsByBrandId(Long brandId);

    // 반납 처리
    void updateReturned(Long rentalId);

    // 연체 상태 갱신
    void updateOverdueStatus();

    // 대여 등록
    void rentItem(Rental rental);

    // 유저의 현재 대여중인 상품 개수 조회
    int countActiveRentalsByUserId(Long userId);

    // 배송완료 처리
    int completeDelivery(Long rentalId);
}
