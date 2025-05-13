package com.lec.spring.repository;

import com.lec.spring.domain.Item;

import java.util.List;

public interface ItemRepository {

    // 아이템 등록(INSERT) <- Item (상품명, 종류, 상품설명, 대여가능여부, 상품상태)
    int save(Item item);

    Item findById(Long id);

    List<Item> findAll();

    List<Item> findByCategory(String category);

    int update(Item item);

    int delete(Long id);
}

