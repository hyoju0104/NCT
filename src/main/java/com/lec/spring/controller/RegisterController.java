package com.lec.spring.controller;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.domain.User;
import com.lec.spring.service.BrandAttachmentService;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Value("${app.upload.path.brand}")
    private String uploadDirBrand;
    
    private final UserService userService;
    private final BrandService brandService;
    private final BrandAttachmentService brandAttachmentService;

    public RegisterController(UserService userService, BrandService brandService, BrandAttachmentService brandAttachmentService) {
        this.userService = userService;
        this.brandService = brandService;
        this.brandAttachmentService = brandAttachmentService;
    }
    

    //회원가입
    @GetMapping("/kind")
    public void registerKind(){}

    @GetMapping("/user")
    public void registerUser(){}

    @PostMapping("/user")
    public String processUserJoin(User user, HttpServletRequest request, RedirectAttributes redirectAttrs){
        //HttpServletRequest request: 폼 안에 있는 rePassword 값은 User 객체에 자동으로 안 담기니까 이걸로 따로 꺼내줘야 해

        //1. 비밀번호 일치 확인
        String rePassword = request.getParameter("rePassword"); //사용자가 폼에 입력한 비번확인칸 값 꺼내옴
        if(!user.getPassword().equals(rePassword)){
            redirectAttrs.addAttribute("error","password");
            return "redirect:/register/user";
        }
        //2. 아이디 중복 확인
        if(userService.isExist(user.getUsername())){
            redirectAttrs.addAttribute("error","exist");
            return "redirect:/register/user";
        }
        //3. 회원가입 처리
        userService.register(user);
        return "redirect:/login";

    }

    
    @GetMapping("/brand")
    public void registerBrand(){}

    @PostMapping("/brand")
    public String processBrandJoin(
            @ModelAttribute Brand brand, // form에서 입력한 이름, 아이디, 비번 등을 자동으로 Brand 객체에 담아줌. 예: <input name="username"> → brand.getUsername()
            @RequestParam("logo") MultipartFile logo, //사용자가 첨부한 로고 이미지 파일을 logoFile이라는 변수로 받음. <input type="file" name="logo"> 이거 ㅇㅇ
            HttpServletRequest request, //사용자가 보낸 모든 요청 직접 확인할 수 있는 객체(rePassword 값 꺼내쓰려고)
            RedirectAttributes redirectAttrs //회원가입 실패 시 다시 회원가입 페이지로 돌아갈 때 이유를 같이 보내기 위해 사용
    ) {
        // 1. 비밀번호 일치 확인
        String rePassword = request.getParameter("rePassword");
        if (!brand.getPassword().equals(rePassword)) {
            redirectAttrs.addAttribute("error", "password");
            return "redirect:/register/brand";
        }

        // 2. 아이디 중복 확인
        if (brandService.isExist(brand.getUsername())) {
            redirectAttrs.addAttribute("error", "exist");
            return "redirect:/register/brand";
        }

        // 3. 회원가입 먼저 수행 (brand.id가 생성됨)
        brandService.register(brand);

        // 4. 첨부파일 저장 (로고 업로드)
        if (logo != null && !logo.isEmpty()) {
            String originalName = logo.getOriginalFilename();
            String newFileName = UUID.randomUUID() + "_" + originalName;
            Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDirBrand);
            File newFile = uploadPath.resolve(newFileName).toFile();

            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                logo.transferTo(newFile);
            } catch (IOException e) {
                redirectAttrs.addFlashAttribute("error", "파일 저장 중 오류가 발생했습니다.");
                return "redirect:/register/brand";
            }

            BrandAttachment attachment = new BrandAttachment();
            attachment.setBrandId(brand.getId());
            attachment.setSourcename(originalName);
            attachment.setFilename(newFileName);

            brandAttachmentService.save(attachment);
        }


        return "redirect:/login";
    }

}
