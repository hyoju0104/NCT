package com.lec.spring.service;

import com.lec.spring.domain.Comment;
import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CommentRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	public CommentServiceImpl(SqlSession sqlSession) {
		this.commentRepository = sqlSession.getMapper(CommentRepository.class);
		this.userRepository = sqlSession.getMapper(UserRepository.class);
		System.out.println("✅ CommentService() 생성");
	}
	
	@Override
	public QryCommentList list(Long postId) {
		
		QryCommentList list = new QryCommentList();
		
		List<Comment> comments = commentRepository.findByPost(postId);
		
		list.setCount(comments.size());
		list.setList(comments);
		list.setStatus("OK");
		
		return list;
		
	}
	
	@Override
	public QryResult write(Long postId, Long userId, String content) {
		
		User user = userRepository.findById(userId);
		Comment comment = Comment.builder()
				.user(user)
				.content(content)
				.postId(postId)
				.build();
		
		
		int cnt = commentRepository.save(comment);
		
		QryResult result = QryResult.builder()
				.count(cnt)
				.status("OK")
				.build();
		
		return result;
		
	}
	
	@Override
	public QryResult delete(Long id) {
		
		int cnt = commentRepository.deleteById(id);
		String status = "FAIL";
		
		if (cnt > 0) status = "OK";
		
		QryResult result = QryResult.builder()
				.count(cnt)
				.status(status)
				.build();
		
		return result;
	}
	
}
