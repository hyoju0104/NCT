package com.lec.spring.service;

import com.lec.spring.domain.PostAttachment;
import com.lec.spring.repository.PostAttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	private final PostAttachmentRepository attachmentRepository;
	
	public AttachmentServiceImpl(SqlSession sqlSession) {
		this.attachmentRepository = sqlSession.getMapper(PostAttachmentRepository.class);
		System.out.println("✅ AttachmentService() 생성");
	}
	
	@Override
	public PostAttachment findById(Long id) {
		return attachmentRepository.findById(id);
	}
	
}
