package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.FileHandler;
import com.ezen.spring.handler.FileSweeper;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@RequestMapping("/board/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService bsv;
    private final FileHandler fh;
    private final FileSweeper fs;

    @GetMapping("/register")
    public String register() {
        return "/board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO bvo,
                           @RequestParam(name="files", required = false)MultipartFile[] files) {
        log.info("BoardVO > {}", bvo);
        List<FileVO> flist = null;
        if(files != null || files[0].getSize() > 0 ) {
            flist = fh.upLoadFiles(files);
            log.info("flist >> {}", flist);
        }
        int isOk = bsv.register(new BoardDTO(bvo, flist));
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
        m.addAttribute("bdto", bsv.getDetail(bno));
        return "/board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO bvo, RedirectAttributes redirectAttributes,
                         @RequestParam(name="files", required = false)MultipartFile[] files){
        List<FileVO> flist = null;
        if(files[0].getSize() > 0 ) {
            flist = fh.upLoadFiles(files);
            log.info("flist >> {}", flist);
        }
        int isOk = bsv.update(new BoardDTO(bvo, flist));
        redirectAttributes.addAttribute("bno", bvo.getBno());
        log.info(">> update > {}", isOk>0? "ok":"fail");
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("bno")long bno) {
        int isOk = bsv.delete(bno);
        log.info(">>> delete > {}", isOk>0? "ok":"fail");
        return "redirect:/board/list";
    }

    // 비동기 첨부파일 삭제임. 혼동 X
    @ResponseBody
    @DeleteMapping("/file/{uuid}")
    public String removeFile(@PathVariable("uuid")String uuid) {
        FileVO fvo = bsv.getFile(uuid);
        int isOk = bsv.removeFile(uuid);

        return isOk > 0 ? "1":"0";
    }


}
