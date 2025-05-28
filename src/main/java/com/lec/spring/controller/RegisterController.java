package com.lec.spring.controller;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.BrandAttachment;
import com.lec.spring.domain.User;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.service.BrandAttachmentService;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
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
    private final UserValidator userValidator;

    public RegisterController(
            UserService userService,
            BrandService brandService,
            BrandAttachmentService brandAttachmentService,
            UserValidator userValidator
    ) {
        this.userService = userService;
        this.brandService = brandService;
        this.brandAttachmentService = brandAttachmentService;
        this.userValidator = userValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }


    //회원가입
    @GetMapping("/kind")
    public void registerKind(){}

    
    @GetMapping("/user")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register/user";
    }

    @PostMapping("/user")
    public String processUserJoin(
            @ModelAttribute("user") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "register/user";
        }

        redirectAttributes.addFlashAttribute("result", userService.register(user));
        return "redirect:/register/registerOk";
    }
    
    @GetMapping("/registerOk")
    public String registerOk() {
        return "register/registerOk";
    }

    
    @GetMapping("/brand")
    public void registerBrand(){}

    @PostMapping("/brand")
    public String processBrandJoin(
            @ModelAttribute Brand brand, // form에서 입력한 이름, 아이디, 비번 등을 자동으로 Brand 객체에 담아줌. 예: <input name="username"> → brand.getUsername()
            @RequestParam("logo") MultipartFile logo, //사용자가 첨부한 로고 이미지 파일을 logoFile이라는 변수로 받음. <input type="file" name="logo"> 이거 ㅇㅇ
            HttpServletRequest request, //사용자가 보낸 모든 요청 직접 확인할 수 있는 객체(rePassword 값 꺼내쓰려고)
            RedirectAttributes redirectAttributes //회원가입 실패 시 다시 회원가입 페이지로 돌아갈 때 이유를 같이 보내기 위해 사용
    ) {
        // 1. 입력값 검증
        boolean hasError  = false;
        
        // 1-1) 아이디 필수 입력
        if (brand.getUsername() == null || brand.getUsername().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_username", "아이디는 필수입니다.");
            hasError = true;
        }
        // 1-2) 아이디 중복 확인
        if (brandService.isExist(brand.getUsername())) {
            redirectAttributes.addFlashAttribute("error_username", "이미 사용 중인 아이디입니다.");
            hasError = true;
        }
        // 1-3) 비밀번호 필수 입력
        if (brand.getPassword() == null || brand.getPassword().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_password", "비밀번호는 필수입니다.");
            hasError = true;
        }
        // 1-4) 비밀번호 일치 확인
        String rePassword = request.getParameter("rePassword");
        if (!brand.getPassword().equals(rePassword)) {
            redirectAttributes.addFlashAttribute("error_rePassword", "비밀번호가 일치하지 않습니다.");
            hasError = true;
        }
        // 1-5) 브랜드명 필수 입력
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_name", "브랜드명은 필수입니다.");
            hasError = true;
        }
        // 1-6) 대표전화번호 필수 입력
        if (brand.getPhoneNum() == null || brand.getPhoneNum().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error_phoneNum", "전화번호는 필수입니다.");
            hasError = true;
        }
        
        // 2. 검증 실패 시 리다이렉트
        if (hasError) {
            redirectAttributes.addFlashAttribute("username", brand.getUsername());
            redirectAttributes.addFlashAttribute("password", brand.getPassword());
            redirectAttributes.addFlashAttribute("rePassword", brand.getRePassword());
            redirectAttributes.addFlashAttribute("name", brand.getName());
            redirectAttributes.addFlashAttribute("phoneNum", brand.getPhoneNum());
            
            return "redirect:/register/brand";
        }

        // 3. 회원가입 먼저 수행 (brand.id가 생성됨)
        redirectAttributes.addFlashAttribute("result", brandService.register(brand));

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
                redirectAttributes.addFlashAttribute("error", "파일 저장 중 오류가 발생했습니다.");
                return "redirect:/register/brand";
            }

            BrandAttachment attachment = new BrandAttachment();
            attachment.setBrandId(brand.getId());
            attachment.setSourcename(originalName);
            attachment.setFilename(newFileName);

            brandAttachmentService.save(attachment);
        }
        
        return "redirect:/register/registerOk";
    }

}
