package com.lec.spring.repository;

// Repository layer(aka. Data layer)
// DataSource (DB) 등에 대한 직접적인 접근

import com.lec.spring.domain.Post;

import java.util.List;

public interface PostRepository {
	
	// 전체 글 목록 : 최신순 (SELECT) -> List<>
	List<Post> findAll();
	
	// 새 글 작성 (INSERT) <- Post(user_id, content, items)
	int save(Post post);
	
	// 특정 id 글 내용 읽기 (SELECT)
	// 만약 해당 id 의 글 없으면 null 리턴함
	Post findById(Long id);
	
	// 특정 id 글 수정 (제목, 내용) (UPDATE) <- Post(id, subject, content)
	int update(Post post);
	
	// 특정 id 글 삭제하기 (DELETE) <= Post(id)
	int delete(Post post);
	
	// 페이징 동작
	// from 부터 rows 개수만큼 읽기 (SELECT)
	List<Post> selectFromRow(int from, int rows);
	
	// 전체 글의 개수 (SELECT)
	int countAll();
	
}
