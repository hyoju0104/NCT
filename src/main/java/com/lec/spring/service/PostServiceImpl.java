package com.lec.spring.service;

import com.lec.spring.domain.Comment;
import com.lec.spring.domain.PostAttachment;
import com.lec.spring.domain.Post;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CommentRepository;
import com.lec.spring.repository.PostAttachmentRepository;
import com.lec.spring.repository.PostRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
	
	@Value("${app.upload.path.post}")
	private String uploadDir;
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostAttachmentRepository postAttachmentRepository;
	private final PostAttachmentService postAttachmentService;
	private final CommentRepository commentRepository;
	
	public PostServiceImpl(SqlSession sqlSession, PostAttachmentRepository postAttachmentRepository,
	                       PostAttachmentService postAttachmentService, CommentRepository commentRepository) {
		this.postRepository = sqlSession.getMapper(PostRepository.class);
		this.userRepository = sqlSession.getMapper(UserRepository.class);
		this.postAttachmentRepository = sqlSession.getMapper(PostAttachmentRepository.class);
		this.postAttachmentService = postAttachmentService;
		this.commentRepository = commentRepository;
		System.out.println("✅ PostService() 생성");
	}
	
	
	// 목록
	@Override
	public List<Post> list() {
		List<Post> posts = postRepository.findAll();
		
		for (Post post : posts) {
			// postId 에 연결된 이미지 파일 전부 조회
			List<PostAttachment> attachments = postAttachmentRepository.findByPostId(post.getId());
			
			if (attachments != null && !attachments.isEmpty()) {
				// id 기준 오름차순으로 정렬
				PostAttachment representative = attachments.stream()
						.min(Comparator.comparing(PostAttachment::getPostId))
						.orElse(null);
				
				// id가 가장 작은 파일만 남기기
				post.setFileList(Collections.singletonList(representative));
			}
			
			// postId 에 연결된 댓글 전부 조회
			List<Comment> comments = commentRepository.findByPost(post.getId());
			post.setCommentList(comments);
			
			// 본문 HTML 태그 제거 후 표시
			String plain = Jsoup.parse(post.getContent()).text();
			post.setContent(plain);
		}
		
		return posts;
	}
	
	@Override
	public int write(Post post, Map<String, MultipartFile> files) {
		
		// 현재 로그인한 작성자 정보
		User user = U.getLoggedUser();
		
		// 위 정보는 session 의 정보이고, 디시 DB 에서 읽어온다.
		user = userRepository.findById(user.getId());
		post.setUser(user);  // 글 작성자 세팅.
		
		int cnt = postRepository.save(post);   // 글 먼저 저장 -> AI 된 PK값(id) 받아오기 가능
		// ✅ 글 작성 완료 후 포인트 500 지급
		userRepository.addPoint(user.getId(), 500);
		
		// 첨부파일 추가
		addFiles(files, post.getId());
		
		return cnt;
		
	}
	
	// 특정 글(id)에 첨부파일(들)(files) 추가
	private void addFiles(Map<String, MultipartFile> files, Long id) {
		
		if (files == null) return;
		
		for (Map.Entry<String, MultipartFile> e : files.entrySet()) {
			// name="upfile##" 인 경우만 첨부파일 등록
			//  -> 다른 웹에디터와 섞이지 않도록 하기 위함 (ex: summernote)
			if (!e.getKey().startsWith("upfile")) continue;
			
			// 첨부파일 정보 출력
			System.out.println("\n➡️첨부파일 정보: " + e.getKey());   // key = name = 값
			U.printFileInfo(e.getValue());  // value = MultipartFile 정보
			System.out.println();
			
			// 물리적인 파일 저장
			PostAttachment file = upload(e.getValue());
			
			// 저장 성공 시, DB 에 파일 저장
			if (file != null) {
				file.setPostId(id);    // FK 설정 (게시물 id)
				postAttachmentRepository.save(file);    // INSERT
			}
			
		}
		
	}
	
	// 물리적으로 서버에 파일 저장. 중복된 파일명은 rename 처리
	private PostAttachment upload(MultipartFile multipartFile) {
		
		PostAttachment attachment = null;
		
		// 담긴 파일이 없으면 pass
		String originalFilename = multipartFile.getOriginalFilename();
		if (originalFilename == null || originalFilename.isEmpty()) return null;
		
		// 원본 파일명
		String sourceName = StringUtils.cleanPath(originalFilename);
		
		// 저장할 파일명
		String fileName = sourceName;
		
		// 파일이 중복되는지 확인
		File file =  new File(uploadDir, fileName); // uploadDir 밑에 있는 filename 찾기
		if (file.exists()) {    // 이미 존재하는 파일명(중복)일 경우, 다른 이름으로 변경하여 저장
			
			// a.txt => a_2378142783946.txt  : time stamp 값을 활용할거다!
			// "a" => "a_2378142783946"  : 확장자 없는 경우
			
			int position = fileName.lastIndexOf(".");
			if (position > -1) { // 확장자가 있는 경우
				String name = fileName.substring(0, position);      // 파일 '이름'
				String extension = fileName.substring(position);    // 파일 '.확장자'
				
				// 중복 방지를 위한 새로운 이름 생성
				fileName = name + "_" + System.currentTimeMillis() + extension;
				
				// 혹시 모를 중복을 피하기 위해 루프로 돌리는 게 좋음
				// 또는 GlobalUniqueID 값을 이용할 수도 있음 (타입명이 엄청 길다)
			}
			else {  // 확장자가 없는 경우
				fileName += "_" + System.currentTimeMillis();
			}
			
		}
		
		// 저장될 파일명
		System.out.println("\tfilenName = " + fileName);
		
		// java.io.* => java.nio.*
		// 실제 저장될 경로명 저장
		Path copyOfLocation = Paths.get(new File(uploadDir, fileName).getAbsolutePath());
		System.out.println("\tcopyOfLocation = " + copyOfLocation);
		
		// 파일 복사
		try {
			Files.copy(
					multipartFile.getInputStream(),
					copyOfLocation,
					StandardCopyOption.REPLACE_EXISTING // 기존에 존재하면 덮어쓰기
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 최종 attachment 설정
		attachment = PostAttachment.builder()
				.filename(fileName) // 저장된 파일명
				.sourcename(sourceName) // 원본 파일명
				.build();
		
		return attachment;
		
	}
	
	@Override
	public Post detail(Long id) {
		Post post = postRepository.findById(id);
		
		if (post != null) {
			// 첨부파일(들) 정보 가져오기
			List<PostAttachment> fileList = postAttachmentRepository.findByPostId(post.getId());
			
			// User 정보 가져오기
			if (post.getUser() == null) {
				User user = postRepository.findUserById(post.getId());
				post.setUser(user);
			}
//			System.out.println(post.getUser().getUsername());
			
			// Post 에 첨부파일 세팅
			post.setFileList(fileList); // 템플릿 엔진에서 받아서 view 생성
		}
		
		return post;
	}
	
	@Override
	public int update(Post post, Map<String, MultipartFile> files, Long[] delfile) {
		int result = 0;
		result = postRepository.update(post);
		
		// 새로운 첨부파일(들) 추가
		addFiles(files, post.getId());
		
		// 삭제할 기존의 첨부파일(들) 삭제
		if (delfile != null) {
			for (Long fileId : delfile) {
				PostAttachment file = postAttachmentRepository.findById(fileId);
				if (file != null) {
					delFile(file);
					postAttachmentRepository.delete(file);
				}
			}
		}
		
		return result;
	}
	
	// 특정 첨부파일을 물리적으로 삭제
	private void delFile(PostAttachment file) {
		
		String saveDir = new File(uploadDir).getAbsolutePath();
		
		File f = new File(saveDir, file.getFilename());
		System.out.println("삭제 시도 : " + f.getAbsolutePath());
		
		if (f.exists()) {   // 파일이 존재하는 경우 삭제
			if (f.delete()) System.out.println("삭제 성공");
			else System.out.println("삭제 실패");
		}
		else System.out.println("파일이 존재하지 않습니다.");
		
	}
	
	
	@Override
	@Transactional
	public int deleteById(Long id) {
		// 연결된 첨부파일 모두 삭제
		postAttachmentService.deleteByPostId(id);
		// 게시글 레코드 삭제
		return postRepository.delete(postRepository.findById(id));
	}

	@Override
	public List<Post> findByUserId(Long userId) {
		return postRepository.findByUserId(userId);
	}
}
