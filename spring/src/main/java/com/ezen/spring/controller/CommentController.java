package com.ezen.spring.controller;


import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/comment/*")
@RestController
@RequiredArgsConstructor            // 필수 생성자 생성 역할 : CommentService
public class CommentController {
    private final CommentService csv;


    // @ResponcetBody 처리하지 않으면 html 을 찾기 때문에 처리하는 값과 다름.
    /*@PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody CommentVO cvo) {
        log.info(">>> comment post > {}",cvo);
        int isOk = csv.post(cvo);
        return new ResponseEntity<String>("1", HttpStatus.OK);
    }*/

    @PostMapping("/post")
    @ResponseBody
    public String post(@RequestBody CommentVO cvo) {
        log.info(">>> comment post > {}",cvo);
        int isOk = csv.post(cvo);
        return isOk>0? "1":"0";
    }

    @GetMapping("/list/{bno}/{page}")
    @ResponseBody
    public PagingHandler list(@PathVariable("bno")long bno, @PathVariable("page") int page) {
        PagingVO pgvo = new PagingVO(page, 5);
        PagingHandler ph = csv.getList(bno, pgvo);
        return ph;
    }

    @DeleteMapping("/remove/{cno}")
    @ResponseBody
    public String remove(@PathVariable("cno") long cno) {
        int isOk = csv.remove(cno);
        return isOk>0? "1":"0";
    }

    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestBody CommentVO cvo) {
        int isOk = csv.update(cvo);
        return isOk>0? "1":"0";
    }
}
