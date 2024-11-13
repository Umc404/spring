package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService bsv;

    @GetMapping("/register")
    public String register() {
        return "/board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO bvo) {
        log.info("BoardVO > {}", bvo);
        int isOk = bsv.register(bvo);
        log.info(">> isOk > {}", isOk > 0? "ok":"fail");
        return "index";
    }

    public String list(Model m){
        List<BoardVO> list = bsv.getList();
        m.addAttribute("list", list);
        return "/board/list";
    }
}
