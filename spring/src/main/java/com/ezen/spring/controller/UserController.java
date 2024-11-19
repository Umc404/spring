package com.ezen.spring.controller;


import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public String userList(Model m) {
        List<UserVO> userList = usv.getList();
        m.addAttribute("userList", userList);
        return "/user/list";
    }

    @GetMapping("/modify")
    public String userInfo() {
        return "/user/modify";
    }

    @PostMapping("/update")
    public String userUpdate(UserVO uvo) {
        if(uvo.getPwd().isEmpty() || uvo.getPwd().length() == 0) {
            int isOk = usv.userUpdatePwdEmpty(uvo);
        } else {
            uvo.setPwd(passwordEncoder.encode(uvo.getPwd()));
            int isOk = usv.userUpdate(uvo);
        }
        return "/index";
    }

    @GetMapping("/delete")
    public String userDelete(@RequestParam("email")String email) {
        int isOk = usv.userDelete(email);
        log.info(">> user delete > {}", isOk>0? "ok":"fail");
        return "/index";
    }
}
