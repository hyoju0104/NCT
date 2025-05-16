package com.lec.spring.service;

import com.lec.spring.domain.BrandAttachment;

import java.util.List;

public interface BrandAttachmentService {

    BrandAttachment findById(Long id);

    List<BrandAttachment> findByBrandId(Long brandId);

    void save(BrandAttachment attachment);

    void deleteById(Long id);

}
