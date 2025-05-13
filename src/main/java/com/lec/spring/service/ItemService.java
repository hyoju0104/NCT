package com.lec.spring.service;

import com.lec.spring.domain.Item;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> list();

    List<Item> findByCategory(String category);

    int write(Item item, Map<String, MultipartFile> files);

    Item detail(Long id);

    int update(Item item, Map<String, MultipartFile> files, Long[] delfile);

    int deleteById(Item item, Map<String, MultipartFile> files, Long[] delfile);
}
