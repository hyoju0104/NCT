package com.lec.spring.repository;

// Repository layer(aka. Data layer)
// DataSource (DB) 등에 대한 직접적인 접근

import com.lec.spring.domain.Post;
import com.lec.spring.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {
	
	// 전체 글 목록 : 최신순 (SELECT) -> List<>
	List<Post> findAll();
	
	// 새 글 작성 (INSERT) <- Post(user_id, content, items)
	int save(Post post);
	
	// 특정 id 글 내용 읽기 (SELECT)
	// 만약 해당 id 의 글 없으면 null 리턴함
	Post findById(@Param("id") Long id);
	
	// 특정 id 글 수정 (제목, 내용) (UPDATE) <- Post(id, subject, content)
	int update(Post post);
	
	// 특정 id 글 삭제하기 (DELETE) <= Post(id)
	int delete(Post post);
	
	// 전체 글의 개수 (SELECT)
	int countAll();

	// 특정 User 의 Post (SELECT)
	List<Post> findByUserId(Long userId);

	// 특정 Post 의 User 정보
	User findUserById(Long id);

}
