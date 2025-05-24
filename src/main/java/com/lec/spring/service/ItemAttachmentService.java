package com.lec.spring.service;

import com.lec.spring.domain.ItemAttachment;

import java.util.List;

public interface ItemAttachmentService {

    // 상품 이미지 저장
    int save(ItemAttachment file);

    // 상품 첨부파일 목록 조회
    List<ItemAttachment> findByItemId(Long itemId);

    // 상품 첨부파일 조회
    ItemAttachment findById(Long id);

    // 상품 첨부파일 삭제
    int deleteById(Long id);
}
