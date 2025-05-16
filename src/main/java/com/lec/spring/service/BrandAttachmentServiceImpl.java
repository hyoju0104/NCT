package com.lec.spring.service;

import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.repository.BrandAttachmentRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandAttachmentServiceImpl implements BrandAttachmentService {

    private final BrandAttachmentRepository brandAttachmentRepository;

    public BrandAttachmentServiceImpl(BrandAttachmentRepository brandAttachmentRepository) {
        this.brandAttachmentRepository = brandAttachmentRepository;
    }

    @Override
    public BrandAttachment findByBrand(Long brandId) {
        return brandAttachmentRepository.findByBrand(brandId);
    }

    @Override
    public void save(BrandAttachment attachment) {
        brandAttachmentRepository.save(attachment);
    }

    @Override
    public void deleteImage(Long brandId) {
        brandAttachmentRepository.deleteImage(brandId);
    }
}
