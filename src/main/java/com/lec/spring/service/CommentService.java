package com.lec.spring.service;

import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;

public interface CommentService {
	
	// 특정 글(postId) 의 댓글 목록
	QryCommentList list(Long postId);
	
	// 특정 글(postId) 에 특정 사용자(userId) 가 댓글 작성
	QryResult write(Long postId, Long userId, String content);
	
	// 특정 댓글(id) 삭제
	QryResult delete(Long id);
	
}
