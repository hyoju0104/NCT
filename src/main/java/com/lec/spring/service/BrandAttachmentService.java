package com.lec.spring.service;

import com.lec.spring.domain.BrandAttachment;

import java.util.List;

public interface BrandAttachmentService {

    // 첨부파일 ID로 정보 조회
    BrandAttachment findById(Long id);

    // 특정 브랜드의 첨부파일 목록 조회
    List<BrandAttachment> findByBrandId(Long brandId);

    // 첨부파일 저장
    void save(BrandAttachment attachment);

    // 첨부파일 삭제
    void deleteById(Long id);
}
