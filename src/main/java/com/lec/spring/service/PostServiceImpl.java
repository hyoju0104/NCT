package com.lec.spring.service;

import com.lec.spring.domain.Post;
import com.lec.spring.repository.PostRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class PostServiceImpl implements PostService {
	
	@Value("${app.pagination.write_pages}")
	private int WRITE_PAGES;    // default = 10
	
	@Value("${app.pagination.page_rows}")
	private int PAGE_ROWS;  // default = 10
	
	
	private final PostRepository postRepository;
	
	public PostServiceImpl(SqlSession sqlSession) {
		postRepository = sqlSession.getMapper(PostRepository.class);
		System.out.println("✅ PostService() 생성");
	}
	
	
	@Override
	public List<Post> list() {
		return List.of();
	}
	
	@Override
	public int write(Post post, Map<String, MultipartFile> files) {
		return 0;
	}
	
	@Override
	public Post detail(Long id) {
		return null;
	}
	
	@Override
	public int update(Post post, Map<String, MultipartFile> files, Long[] delfile) {
		return 0;
	}
	
	@Override
	public int deleteById(Long id) {
		return 0;
	}
	
	@Override
	public List<Post> list(Integer page, Model model) {
		return List.of();
	}
}
