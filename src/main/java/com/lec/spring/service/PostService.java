package com.lec.spring.service;

// Service layer
// - Business logic, Transaction 담당
// - Controller 와 Data 레이어의 분리

import com.lec.spring.domain.Post;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PostService {
	
	// 글 목록
	List<Post> list();
	
	// 글 작성
	int write(Post post, Map<String, MultipartFile> files);
	
	// 특정 id 의 글 읽어오기 (SELECT)
	Post detail(Long id);
	
	// 특정 id 글 수정하기 (제목, 내용)  (UPDATE)
	int update(Post post, Map<String, MultipartFile> files, Long[] delfile);
	
	// 특정 id 의 글 삭제하기 (DELETE)
	int deleteById(Long id);
	
	// 페이징 목록
	List<Post> list(Integer page, Model model);
	
}
