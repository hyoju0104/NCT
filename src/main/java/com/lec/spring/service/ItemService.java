package com.lec.spring.service;

import com.lec.spring.domain.Item;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService {

    // 전체 상품 목록 조회
    List<Item> list();

    // 카테고리 별 상품 목록 조회
    List<Item> findByCategory(String category);

    // 상품 등록
    void save(Item item);

    // 브랜드가 등록한 상품 목록 조회
    List<Item> findByBrandId(Long brandId);

    // 상품 상세 조회
    Item detail(Long id);

    // 상품 정보 수정
    int update(Item item);

    // 상품 사용 불가능 상태로 변경
    int markAsUnavailable(Long id);

    // 상품 존재하지 않은 상태로 변경 (is_exist =false)
    int markAsNotExist(Long id);

    // 상품 사용 가능 여부 설정
    void setAvailable(Long itemId, boolean isAvailable);
}
