package com.lec.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @GetMapping("/login")
    public void login() {}

    //회원가입
    @GetMapping("/register/kind")
    public void registerKind(){}

    @GetMapping("/register/user")
    public void registerUser(){}

    @GetMapping("/register/brand")
    public void registerBrand(){}
}
