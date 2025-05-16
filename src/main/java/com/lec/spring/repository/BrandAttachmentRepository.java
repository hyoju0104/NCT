package com.lec.spring.repository;

import com.lec.spring.domain.BrandAttachment;

public interface BrandAttachmentRepository {

    int save(BrandAttachment file);

    BrandAttachment findByBrand(Long brandId);

    int deleteImage(Long brandId);

}
