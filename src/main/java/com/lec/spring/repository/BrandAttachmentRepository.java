package com.lec.spring.repository;

import com.lec.spring.domain.BrandAttachment;
import java.util.List;

public interface BrandAttachmentRepository {

    // 첨부파일 한개 저장
    int save(BrandAttachment file);

    // 브랜드 ID로 첨부파일 목록 조회
    List<BrandAttachment> findByBrandId(Long brandId);

    // 첨부파일 하나 조회
    BrandAttachment findById(Long id);

    // 첨부파일 삭제
    int deleteById(Long brandId);

}