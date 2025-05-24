package com.lec.spring.controller;

import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.service.CommentService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// 특정 글의 댓글 목록 출력
	@GetMapping("/list/{postId}")
	public QryCommentList list(@PathVariable Long postId) {
		QryCommentList result = commentService.list(postId);
		result.setStatus("OK");
		result.setCount(result.getList().size());
		return result;
	}
	
	// 특정 글의 댓글 작성
	@PostMapping("/write")
	public QryResult write(
			@RequestParam("post_id") Long postId,
			@RequestParam("user_id") Long userId,
			@RequestParam("content") String content
	) {
		return commentService.write(postId, userId, content);
	}
	
	// 특정 글의 댓글 삭제
	@PostMapping("/delete")
	public QryResult delete(Long id) {
		return commentService.delete(id);
	}
	
}
