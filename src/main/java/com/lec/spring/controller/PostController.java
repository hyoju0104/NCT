package com.lec.spring.controller;

import com.lec.spring.domain.Post;
import com.lec.spring.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@RequestMapping("/list")
	public void list(Model model) {
		List<Post> posts = postService.list();
		System.out.println("조회된 게시글 수: " + (posts != null ? posts.size() : 0));
		if (posts != null && !posts.isEmpty()) {
			for (Post post : posts) {
				System.out.println("Post ID: " + post.getId() +
						", Content: " + post.getContent() +
						", User: " + (post.getUser() != null ? post.getUser().getUsername() : "null"));
			}
		}
		model.addAttribute("posts", posts);
	}

}
