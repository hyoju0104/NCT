package com.lec.spring.controller;

import com.lec.spring.domain.Brand;
import com.lec.spring.domain.User;
import com.lec.spring.service.BrandService;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    private final BrandService brandService;

    public RegisterController(UserService userService, BrandService brandService) {
        this.userService = userService;
        this.brandService = brandService;
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
            @RequestParam("logo") MultipartFile logoFile, //사용자가 첨부한 로고 이미지 파일을 logoFile이라는 변수로 받음. <input type="file" name="logo"> 이거 ㅇㅇ
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

        // 3. 로고 파일 저장
        String originalName = logoFile.getOriginalFilename();
        //저장할 파일 이름을 고유하게 만듦(중복 방지용)
        String storedFileName = UUID.randomUUID() + "_" + originalName;

        // /uploads 에 저장
        Path savePath = Paths.get("uploads", storedFileName);
        try {
            Files.copy(logoFile.getInputStream(), savePath);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttrs.addAttribute("error", "file");
            return "redirect:/register/brand";
        }

        // 4. 파일 정보 저장
        brand.setLogoSourcename(originalName);
        brand.setLogoFilename(storedFileName);

        // 5. 회원가입 처리
        brandService.register(brand);

        return "redirect:/login";
    }

}
