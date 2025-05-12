package com.lec.spring.controller;

import com.lec.spring.domain.Post;
import com.lec.spring.service.PostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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
	
	
	@GetMapping("/write")
	public void write(){}
	
	@PostMapping("/write")
	public String writeOk(
			@RequestParam Map<String, MultipartFile> files,  // 첨부파일들 <name, file>
			@Valid Post post,
			BindingResult result,   // Validator 가 유효성 검사를 한 결과가 담긴 객체.
			Model model,    // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
			RedirectAttributes redirectAttributes   // redirect: 시 넘겨줄 값들.
	){
		// validation 에러가 있었다면 redirect 한다!
		if(result.hasErrors()){
			showErrors(result);
			
			// addAttribute(name, value)
			//    request parameters로 값을 전달. redirect URL에 query string 으로 값이 담김
			//    request.getParameter에서 해당 값에 액세스 가능
			// addFlashAttribute(name, value)
			//    일회성. 한번 사용하면 Redirect 후 값이 소멸
			//    request parameters로 값을 전달하지 않음
			//    '객체'로 값을 그대로 전달
			
			// redirect 시, 기존에 입력했던 값들은 보이도록 전달해주어야 한다
			//   전달한 name 들은 => 템플릿에서 사용 가능한 변수!
			redirectAttributes.addFlashAttribute("user", post.getUser());
			redirectAttributes.addFlashAttribute("content", post.getContent());
			redirectAttributes.addFlashAttribute("items", post.getItems());
			
			// 어떤 에러가 발생했는지 정보 전달
			for(FieldError err : result.getFieldErrors()){
				redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
			}
			
			return "redirect:/post/write";  // GET
		}
		
		model.addAttribute("result", postService.write(post, files));
		return "post/writeOk";  // view
	}
	
	
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("post", postService.detail(id));
		return "post/detail";
	}
	
	
	
	@PostMapping("/delete")
	public String delete(Long id, Model model){
		model.addAttribute("result", postService.deleteById(id));
		return "post/deleteOk";
	}
	
	
	// 바인딩 에러 출력 도우미 메소드
	public void showErrors(Errors errors){
		if(errors.hasErrors()){
			System.out.println("💢에러개수: " + errors.getErrorCount());
			// 어떤 field 에 어떤 에러(code) 가 담겨있는지 확인
			System.out.println("\t[field]\t|[code]");
			List<FieldError> errList = errors.getFieldErrors();
			for(FieldError err : errList){
				System.out.println("\t" + err.getField() + "\t|" + err.getCode());
			}
		} else {
			System.out.println("✔에러 없슴");
		}
	} // end showErrors()

}
