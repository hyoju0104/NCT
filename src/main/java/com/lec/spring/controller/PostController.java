package com.lec.spring.controller;

import com.lec.spring.config.PrincipalUserDetails;
import com.lec.spring.domain.Post;
import com.lec.spring.domain.PostAttachment;
import com.lec.spring.domain.PostAttachmentValidator;
import com.lec.spring.domain.PostValidator;
import com.lec.spring.service.PostService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	private final PostAttachmentValidator postAttachmentValidator;
	
	public PostController(PostService postService, PostAttachmentValidator postAttachmentValidator) {
		this.postService = postService;
		this.postAttachmentValidator = postAttachmentValidator;
	}
	
	private static final List<String> IMAGE_MIME_TYPES = Arrays.asList(
			"image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp"
	);
	
	
	@InitBinder("post")
	public void initBinder(WebDataBinder binder){
		System.out.println("✅ @InitBinder 호출");
		
		// 1. 바인딩된 Post 객체 가져오기
		Object target = binder.getTarget();
		if (target instanceof Post) {
			// 2. SecurityContext 에서 인증 정보 꺼내기
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			// 3. Post.user 주입
			if (auth != null && auth.getPrincipal() instanceof PrincipalUserDetails) {
				PrincipalUserDetails pud = (PrincipalUserDetails) auth.getPrincipal();
				((Post) target).setUser(pud.getUser());
			}
		}
		
		// 4. 기존 PostValidator 등록
		binder.addValidators(new PostValidator());
	}
	
	@ModelAttribute("post")
	public Post getPost(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			return postService.detail(id);
		}
		return new Post();
	}
	
	
	@RequestMapping("/list")
	public void list(Model model) {
		List<Post> posts = postService.list();
		model.addAttribute("posts", posts);
	}
	
	
	@GetMapping("/write")
	public void write(){}
	
	@PostMapping("/write")
	public String writeOk(
		 	@Valid Post post,
			BindingResult result,   // Validator 가 유효성 검사를 한 결과가 담긴 객체.
		    MultipartHttpServletRequest request,    // 첨부파일들 <name, file>
			Model model,    // 매개변수 선언시 BindingResult 보다 Model 을 뒤에 두어야 한다.
			RedirectAttributes redirectAttributes,  // redirect: 시 넘겨줄 값들.
			@AuthenticationPrincipal PrincipalUserDetails principal   // 로그인된 사용자 정보
	){
		// 1. 로그인 체크
		if (principal == null) {
			redirectAttributes.addFlashAttribute("error", "로그인 후 작성 가능합니다.");
			return "redirect:/post/list";
		}
		post.setUser(principal.getUser());
		
		// 2. PostValidator 수행 (자동)
		
		// 3. PostAttachmentValidator 수행 (파일별 수동 검증) : 이미지 최소 1개 업로드 검증
		//                                                      Map→List 로 변환해서 인덱스 접근 가능토록 함
		// 3-1) 업로드된 파일 전체 Map 으로 꺼내기
		Map<String, MultipartFile> files = request.getFileMap();
		
		// 3-2) 실제로 선택된(비어있지 않은) 파일만 골라내기
		List<MultipartFile> fileList = files.values().stream()
				.filter(f -> !f.isEmpty())
				.collect(Collectors.toList());
		
		// 3-3) 이미치 최소 1개 존재 여부 검증
		if (fileList.isEmpty()) {
			result.rejectValue("fileList", "1개 이상의 이미지를 등록해주세요.");
		}
		else {
			// 3-4) 파일 개별 검증 : 이미지 파일 여부 확인
			for (MultipartFile file : fileList) {
				postAttachmentValidator.validate(file, result);
			}
		}
		
		// 4. 검증 에러 처리 : validation 에러가 있었다면 redirect
		if(result.hasErrors()){
			// 에러 원인 콘솔에 출력
			showErrors(result);
			
			// 어떤 에러가 발생했는지 정보 전달
			for(FieldError err : result.getFieldErrors()){
				redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
			}
			
			redirectAttributes.addFlashAttribute("user", post.getUser());
			redirectAttributes.addFlashAttribute("content", post.getContent());
			redirectAttributes.addFlashAttribute("items", post.getItems());
			redirectAttributes.addFlashAttribute("fileList", post.getFileList());
			
			return "redirect:/post/write";  // GET
		}
		
		// 5. 저장
		model.addAttribute("result", postService.write(post, files));
		return "post/writeOk";  // view
	}
	
	
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("post", postService.detail(id));
		return "post/detail";
	}
	
	
	@GetMapping("/update/{id}")
	public String update(
			@PathVariable Long id,
			@ModelAttribute Post post,
			Model model,
			@AuthenticationPrincipal PrincipalUserDetails principal  // 작성자 검증용
	) {
		// 1) flash 에 없으면 DB 에서 로딩
		post = postService.detail(id);
		
		// 2) 작성자 권한 확인
		if (principal == null || !principal.getUser().getId().equals(post.getUser().getId())){
			return "redirect:/post/list";
		}
		
		model.addAttribute("post", post);
		return "post/update";
	}
	
	@PostMapping("/update")
	public String updateOk(
			@Valid @ModelAttribute("post") Post post,
			BindingResult result,
//			@RequestParam Map<String, MultipartFile> files, // 새로 추가되는 첨부파일(들) 정보
			MultipartHttpServletRequest request,
			Model model,
			RedirectAttributes redirectAttributes,
			@RequestParam(required = false) Long[] delFile     // 삭제될 파일들의 id(들)
	){
		System.out.println("▶▶▶ detail 호출 id=" + post.getId());
		Post originalPost = postService.detail(post.getId());
		System.out.println("▶▶▶ detail 리턴=" + originalPost);
		
		if (originalPost == null) return "redirect:/post/list";
		
		List<PostAttachment> fileList = originalPost.getFileList();
		
		if(result.hasErrors()){
			showErrors(result);
			
			// DB 첨부파일만 다시 세팅
			var originalFileList = postService.detail(post.getId()).getFileList();
			post.setFileList(originalFileList);
			
			redirectAttributes.addFlashAttribute("user", post.getUser());
			redirectAttributes.addFlashAttribute("post", post);
			redirectAttributes.addFlashAttribute("content", post.getContent());
			redirectAttributes.addFlashAttribute("items", post.getItems());
			redirectAttributes.addFlashAttribute("delFile", delFile);
			redirectAttributes.addFlashAttribute("originalFileList", originalFileList);
			
			for(FieldError err : result.getFieldErrors()){
				redirectAttributes.addFlashAttribute("error_" + err.getField(), err.getCode());
			}
			
			return "redirect:/post/update/" + post.getId();
		}
		
		Map<String, MultipartFile> files = request.getFileMap();
		int cnt = postService.update(post, files, delFile);
		model.addAttribute("result", cnt);
		return "post/updateOk";
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
			System.out.println("✅ 에러 없음");
		}
	} // end showErrors()

}
