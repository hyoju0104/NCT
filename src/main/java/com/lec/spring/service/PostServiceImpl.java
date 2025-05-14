package com.lec.spring.service;

import com.lec.spring.domain.Attachment;
import com.lec.spring.domain.Post;
import com.lec.spring.domain.User;
import com.lec.spring.repository.AttachmentRepository;
import com.lec.spring.repository.PostRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
	
	@Value("${app.upload.path.post}")
	private String uploadDir;
	
	@Value("${app.pagination.write_pages}")
	private int WRITE_PAGES;    // default = 10
	
	@Value("${app.pagination.page_rows}")
	private int PAGE_ROWS;  // default = 10
	
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AttachmentRepository attachmentRepository;
	
	public PostServiceImpl(SqlSession sqlSession) {
		this.postRepository = sqlSession.getMapper(PostRepository.class);
		this.userRepository = sqlSession.getMapper(UserRepository.class);
		this.attachmentRepository = sqlSession.getMapper(AttachmentRepository.class);
		System.out.println("✅ PostService() 생성");
	}
	
	
	// 목록
	@Override
	public List<Post> list() {
		return postRepository.findAll();
	}
	
	@Override
	public int write(Post post, Map<String, MultipartFile> files) {
		
		// 현재 로그인한 작성자 정보
		User user = U.getLoggedUser();
		
		// 위 정보는 session 의 정보이고, 디시 DB 에서 읽어온다.
		user = userRepository.findById(user.getId());
		post.setUser(user);  // 글 작성자 세팅.
		
		int cnt = postRepository.save(post);   // 글 먼저 저장 -> AI 된 PK값(id) 받아오기 가능
		
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
			Attachment file = upload(e.getValue());
			
			// 저장 성공 시, DB 에 파일 저장
			if (file != null) {
				file.setPostId(id);    // FK 설정 (게시물 id)
				attachmentRepository.save(file);    // INSERT
			}
			
		}
		
	}
	
	// 물리적으로 서버에 파일 저장. 중복된 파일명은 rename 처리
	private Attachment upload(MultipartFile multipartFile) {
		
		Attachment attachment = null;
		
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
		attachment = Attachment.builder()
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
			List<Attachment> fileList = attachmentRepository.findByPostId(post.getId());
			
			// Post 에 첨부파일 세팅
			post.setFileList(fileList); // 템플릿 엔진에서 받아서 view 생성
		}
		
		return post;
	}
	
	@Override
	public int update(Post post, Map<String, MultipartFile> files, Long[] delfile) {
		return postRepository.update(post);
	}
	
	@Override
	public int deleteById(Long id) {
		return postRepository.delete(postRepository.findById(id));
	}

	@Override
	public List<Post> findByUserId(Long userId) {
		return postRepository.findByUserId(userId);
	}
}
