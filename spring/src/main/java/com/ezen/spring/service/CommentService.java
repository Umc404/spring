package com.ezen.spring.service;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import org.springframework.data.repository.query.Param;

public interface CommentService {
    int post(CommentVO cvo);

    PagingHandler getList(long bno, PagingVO pgvo);

    int remove(long cno);

    int update(CommentVO cvo);
}
