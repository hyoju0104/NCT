package com.lec.spring.repository;

import com.lec.spring.domain.ItemAttachment;

import java.util.List;

public interface ItemAttachmentRepository {

    // 첨부파일 저장
    int save(ItemAttachment file);

    // 특정 상품의 첨부파일 목록 조회
    List<ItemAttachment> findByItemId(Long itemId);

    // 첨부파일 ID로 상세 정보 조회
    ItemAttachment findById(Long id);

    // 첨부파일 삭제
    int deleteById(ItemAttachment file);
}
