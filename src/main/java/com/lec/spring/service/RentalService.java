package com.lec.spring.service;
import com.lec.spring.domain.Rental;
import java.util.List;

public interface RentalService {

    int createRental(Rental rental);                      // 대여 생성

    Rental findRentalById(Long id);                        // 단건 조회

    List<Rental> findAllByUserId(Long userId);         // 유저의 대여 목록

    List<Rental> findRentalsByBrandId(Long brandId);       // 브랜드 등록 아이템 대여 목록

    void updateReturned(Long rentalId);                    // 반납 처리

    void updateOverdueStatus();                         // 연체 상태 갱신+

    void rentItem(Rental rental);

    int countActiveRentalsByUserId(Long userId);

}
