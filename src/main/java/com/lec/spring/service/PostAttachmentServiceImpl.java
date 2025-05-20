package com.lec.spring.service;

import com.lec.spring.domain.PostAttachment;
import com.lec.spring.repository.PostAttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostAttachmentServiceImpl implements PostAttachmentService {
	
	@Value("${app.upload.path.post}")
	private String uploadDir;
	
	private final PostAttachmentRepository attachmentRepository;
	
	public PostAttachmentServiceImpl(SqlSession sqlSession) {
		this.attachmentRepository = sqlSession.getMapper(PostAttachmentRepository.class);
		System.out.println("✅ AttachmentService() 생성");
	}
	
	
	@Override
	public PostAttachment findById(Long id) {
		return attachmentRepository.findById(id);
	}
	
	@Override
	public void deleteAttachment(Long id) {
		PostAttachment attachment = attachmentRepository.findById(id);
		
		if (attachment != null) {
			Path file = Paths.get(uploadDir, attachment.getFilename());
			try {
				Files.deleteIfExists(file);
				System.out.println("✅ 파일 삭제 : " + file);
			} catch (IOException e) {
				System.out.println("‼️ 파일 삭제 실패 : " + file + " -> " + e.getMessage());
				throw new RuntimeException(e);
			}
			attachmentRepository.delete(attachment);
		}
	}
	
	@Transactional  // 하나의 트랜잭션으로 묶기 (모두 다 commit 되거나 rollback 되거나)
	@Override
	public void deleteByPostId(Long postId) {
		List<PostAttachment> fileList = attachmentRepository.findByPostId(postId);
		if (fileList != null) {
			fileList.forEach(attachment -> deleteAttachment(attachment.getId()));
		}
	}
	
}
