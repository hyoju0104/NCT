package com.lec.spring.repository;

import com.lec.spring.domain.PostAttachment;

import java.util.List;
import java.util.Map;

public interface PostAttachmentRepository {

	// 특정 게시글(postId)에 첨부파일(들) INSERT
	int insert(List<Map<String, Object>> list, Long postId);
	
	// 첨부파일 한개 저장 INSERT
	int save(PostAttachment file);
	
	// 특정 게시글(postId)의 첨부파일(들) SELECT
	List<PostAttachment> findByPostId(Long postId);
	
	// 특정 첨부파일(id) 1개 SELECT
	PostAttachment findById(Long id);
	
	// 선택된 첨부파일(들) SELECT
	// 게시글 수정 시 사용
	List<PostAttachment> findByIds(Long[] ids);
	
	// 선택된 첨부파일(들) DELETE
	// 게시글 수정 시 사용
	int deleteByIds(Long[] ids);
	
	// 특정 첨부 파일(file) DELETE
	int delete(PostAttachment file);
	
}
