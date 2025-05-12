package com.lec.spring.service;

import com.lec.spring.domain.Attachment;
import com.lec.spring.domain.Post;
import com.lec.spring.repository.PostRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
	
	@Value("${app.pagination.write_pages}")
	private int WRITE_PAGES;    // default = 10
	
	@Value("${app.pagination.page_rows}")
	private int PAGE_ROWS;  // default = 10
	
	
	private final PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
		System.out.println("✅ PostService() 생성");
	}
	
	
	// 목록
	@Override
	public List<Post> list() {
		return postRepository.findAll();
	}
	
	// pagenation 구현
	@Override
	public List<Post> list(Integer page, Model model) {
		return List.of();
	}
	
	@Override
	public int write(Post post, Map<String, MultipartFile> files) {
		
		int cnt = postRepository.save(post);
		
		return cnt;
		
	}
	
	@Override
	public Post detail(Long id) {
		Post post = postRepository.findById(id);
		/*
		if (post != null) {
			// 첨부파일(들) 정보 가져오기
			List<Attachment> fileList = attachmentRepository.findByPost(post.getId());
			
			// '이미지 파일 여부' 세팅
			setImage(fileList);
			
			// Post 에 첨부파일 세팅
			post.setFileList(fileList); // 템플릿 엔진에서 받아서 view 생성
		}
		*/
		return post;
	}
	
	@Override
	public int update(Post post, Map<String, MultipartFile> files, Long[] delfile) {
		return 0;
	}
	
	@Override
	public int deleteById(Long id) {
		return 0;
	}
}
