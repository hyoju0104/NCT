package com.lec.spring.service;

import com.lec.spring.domain.BrandAttachment;

public interface BrandAttachmentService {

    BrandAttachment findByBrand(Long brandId);

    void save(BrandAttachment attachment);

    void deleteImage(Long brandId);
}
