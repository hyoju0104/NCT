package com.lec.spring.repository;

import com.lec.spring.domain.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemRepository {

    List<Item> findByBrandId(Long brandId);

    int save(Item item);

    Item findById(Long id);

    List<Item> findAll();

    List<Item> findByCategory(String category);

    int markAsUnavailable(Long id);

    int update(Item item);

    int markAsNotExist(Long id);

    int markAllItemsAsNotExistByBrandId(Long brandId);

    void setAvailable(@Param("itemId") Long itemId, @Param("isAvailable") boolean isAvailable);

}

