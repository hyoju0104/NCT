package com.lec.spring.service;

import com.lec.spring.domain.Attachment;
import com.lec.spring.repository.AttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	private final AttachmentRepository attachmentRepository;
	
	public AttachmentServiceImpl(SqlSession sqlSession) {
		this.attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
	}
	
	@Override
	public Attachment findById(Long id) {
		return attachmentRepository.findById(id);
	}
	
}
