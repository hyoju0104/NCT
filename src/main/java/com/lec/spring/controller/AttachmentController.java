package com.lec.spring.controller;

import com.lec.spring.service.AttachmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttachmentController {
	
	@Value("{app.upload.path}")
	public String uploadDir;
	
	private final AttachmentService attachmentService;
	
	public AttachmentController(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	
	
}
