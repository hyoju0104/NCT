package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/mypage/detail")
    public void showMyPage(
            @AuthenticationPrincipal PrincipalDetails principalDetails, //로그인한 사용자 정보 가져오기
            Model model //뷰에 데이터 넘기기 위함
            ){
        //1. 로그인한 사용자 정보 가져오기
        User loginUser = principalDetails.getUser();
        //2.Thymeleaf로 넘길 데이터 세팅
        model.addAttribute("user", loginUser);
    }
}