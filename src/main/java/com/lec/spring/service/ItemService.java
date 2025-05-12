package com.lec.spring.service;

import com.lec.spring.domain.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> list();

    int write(Item item, Map<String, MultipartFile> files);

    Item detail(Long id);

    // 특정 id 글 수정하기 (제목, 내용)  (UPDATE)
    int update(Item item, Map<String, MultipartFile> files, Long[] delfile);

    // 특정 id 의 글 삭제하기 (DELETE) -> status 값만 수정
    int deleteById(Item item, Map<String, MultipartFile> files, Long[] delfile);
}
