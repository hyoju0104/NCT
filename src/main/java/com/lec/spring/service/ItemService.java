package com.lec.spring.service;

import com.lec.spring.domain.Item;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> list();

    List<Item> findByCategory(String category);

    void save(Item item);

    List<Item> findByBrandId(Long brandId);

    Item detail(Long id);

    int update(Item item);

    int markAsUnavailable(Long id);

    int markAsNotExist(Long id);

    void setAvailable(Long itemId, boolean isAvailable);

}
