package com.lec.spring.controller;

import com.lec.spring.domain.User;
import com.lec.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
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

        return "redirect:/login";

    }

    @GetMapping("/brand")
    public void registerBrand(){}
}
