package com.lec.spring.service;

import com.lec.spring.domain.PostAttachment;

public interface PostAttachmentService {
	
	// Post CRUD 와 같이 기능하도록 함
	PostAttachment findById(Long id);
	
	// 단일 첨부파일 삭제
	void deleteAttachment(Long id);
	
	// 특정 Post 에 연결된 모든 첨부파일 삭제
	void deleteByPostId(Long postId);
	
}
