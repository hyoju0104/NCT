package com.lec.spring.util;

import com.lec.spring.config.UserDetails;
import com.lec.spring.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class U {
	
	// 현재 request 구하기
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attrs.getRequest();
	}
	
	// 현재 session 구하기
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	// 현재 로그인한 사용자 User 구하기
	public static User getLoggedUser() {
		// 현재 로그인한 사용자
		// Security ContextHolder 라는 키값에다가 session 정보를 저장함
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return user;
	}

	// 첨부파일 정보(MultipartFile) 출력하기
	public static void printFileInfo(MultipartFile file) {
		
		String originalFilename = file.getOriginalFilename();   // 원본 파일명
		
		if (originalFilename == null || originalFilename.isEmpty())  {
			System.out.println("\t파일이 없습니다.");
			return;
		}
		
		// 일반 파일인 경우
		System.out.println("""
					Original File Name : %s
					CleanPath : %s
					File Size : %s
					MIME_Type : %s
				""".formatted(
						originalFilename,
						StringUtils.cleanPath(originalFilename),    //   ※  cleanPath 는  C:\Users\aaa\bbbb/dsaf/asdfsafd.ddd
																	 //                    "\" -> "/" 로 변경
						file.getSize() + "bytes",   // 용량 (byte 단위)
						file.getContentType()       // content type (MIME type)
		));
		
		// 이미지 파일인 경우
		try {
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
			
			if (bufferedImage != null) {
				System.out.printf("\t이미지 파일입니다: %d x %d", bufferedImage.getWidth(), bufferedImage.getHeight());
			}
			else {
				System.out.println("\t이미지 파일이 아닙니다");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
