package com.lec.spring.service;

import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.repository.BrandAttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandAttachmentServiceImpl implements BrandAttachmentService {

    private final BrandAttachmentRepository brandAttachmentRepository;

    public BrandAttachmentServiceImpl(SqlSession sqlSession) {
        this.brandAttachmentRepository = sqlSession.getMapper(BrandAttachmentRepository.class);
    }

    @Override
    public BrandAttachment findById(Long brandId) {
        return brandAttachmentRepository.findById(brandId);
    }

    @Override
    public List<BrandAttachment> findByBrandId(Long brandId) {
        return brandAttachmentRepository.findByBrandId(brandId);
    }

    @Override
    public void save(BrandAttachment attachment) {
        brandAttachmentRepository.save(attachment);
    }

    @Override
    public void deleteById(Long brandId) {
        brandAttachmentRepository.deleteById(brandId);
    }
}
