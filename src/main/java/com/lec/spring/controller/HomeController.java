package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	// "/" 요청이 들어오면 /post/list 로 리다이렉트
	@RequestMapping("/")
	public String root() {
		return "redirect:/post/list";
	}
	
	// "/home" 요청이 들어오면 /post/list 로 리다이렉트
	@RequestMapping("/home")
	public String home() {
		return "redirect:/post/list";
	}
	
}
