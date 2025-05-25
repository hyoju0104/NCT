package com.lec.spring.repository;

import com.lec.spring.domain.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemRepository {

    // 상품 리스트 조회
    List<Item> findByBrandId(Long brandId);

    // 상품 등록
    int save(Item item);

    // 상품 상세 조회
    Item findById(Long id);

    // 전체 상품 조회
    List<Item> findAll();

    // 카테고리 별 상품 조회
    List<Item> findByCategory(String category);

    // 상품 비활성 상태 설정
    int markAsUnavailable(Long id);

    // 상품 정보 수정
    int update(Item item);

    // 상품 존재하지 않는 것으로 표시 (is_exist = false)
    int markAsNotExist(Long id);

    // 해당 브랜드가 등록한 모든 상품을 존재하지 않음 처리
    // 특정 브랜드가 탈퇴하면 모든 상품 is_exist false 처리
    int markAllItemsAsNotExistByBrandId(Long brandId);

    // 상품의 사용 가능 여부 설정
    // 특정 사용자가 대여를 했을 경우 is_available false, 반납하면 true
    void setAvailable(@Param("itemId") Long itemId, @Param("isAvailable") boolean isAvailable);

}

