package com.lec.spring.repository;

import com.lec.spring.domain.Attachment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AttachmentRepository {

	// 특정 게시글(postId)에 첨부파일(들) INSERT
	int insert(List<Map<String, Object>> list, Long postId);
	
	// 첨부파일 한개 저장 INSERT
	int save(Attachment file);
	
	// 특정 게시글(postId)의 첨부파일(들) SELECT
	List<Attachment> findByPostId(Long postId);
	
	// 특정 첨부파일(id) 1개 SELECT
	Attachment findById(Long id);
	
	// 선택된 첨부파일(들) SELECT
	// 게시글 수정 시 사용
	List<Attachment> findByIds(Long[] ids);
	
	// 선택된 첨부파일(들) DELETE
	// 게시글 수정 시 사용
	int deleteByIds(Long[] ids);
	
	// 특정 첨부 파일(file) DELETE
	int delete(Attachment file);
	
}
