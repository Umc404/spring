package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/list")
    public String list(Model m, PagingVO pgvo){

        int totalCount = bsv.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        m.addAttribute("list", bsv.getList(pgvo));
        m.addAttribute("ph", ph);
        return "/board/list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("bno")long bno, Model m) {
        m.addAttribute("bvo", bsv.getDetail(bno));
        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo, RedirectAttributes redirectAttributes){
        int isOk = bsv.update(bvo);
        redirectAttributes.addAttribute("bno", bvo.getBno());
        log.info(">> update > {}", isOk>0? "ok":"fail");
        return "redirect:/board/detail";
    }


}
