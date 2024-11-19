package com.ezen.spring.controller;


import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/user/*")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService usv;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/signup")
    public String signup(){
        return "/user/register";
    }

    @PostMapping("/signup")
    public String signup(UserVO uvo) {
        uvo.setPwd(passwordEncoder.encode(uvo.getPwd()));
        int isOk = usv.register(uvo);
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";

    }

}
