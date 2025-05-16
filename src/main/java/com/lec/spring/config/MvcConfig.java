package com.lec.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	@Value("${app.upload.path.post}")
	private String uploadDir;   // "/upload/post"

	@Value("${app.upload.path.brand}")
	private String uploadDirBrand;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("✅ MvcConfig.addResourceHandlers() 호출");
		
		// 설정한 URL 로 request 받으면 "upload/" 경로의 static resource 가 응답되도록 함.
		registry
				.addResourceHandler("/upload/post/**")
				.addResourceLocations("file:" + uploadDir + "/");

//		registry.addResourceHandler("/upload/item/**")
//				.addResourceLocations("file:" + "/" + uploadDirItem + "/");

		registry.addResourceHandler("/upload/brand/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + "/" + uploadDirBrand + "/");
	}
	
//	@Bean
//	public StandardServletMultipartResolver multipartResolver() {
//		return new StandardServletMultipartResolver();
//	}
	
}
