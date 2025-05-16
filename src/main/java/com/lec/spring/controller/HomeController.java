package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String root() {
		// "/" 요청이 들어오면 /post/list 로 리다이렉트
		return "redirect:/post/list";
	}
	
}
