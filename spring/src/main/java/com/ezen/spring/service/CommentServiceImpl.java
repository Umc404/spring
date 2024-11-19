package com.ezen.spring.service;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    @Override
    public int post(CommentVO cvo) {
        return commentMapper.post(cvo);
    }

    @Override
    public PagingHandler getList(long bno, PagingVO pgvo) {
        int totalCount = commentMapper.getTotalCount(bno);
        List<CommentVO> cmtList = commentMapper.getList(pgvo, bno);
        PagingHandler ph = new PagingHandler(pgvo, totalCount, cmtList);
        return ph;
    }

    @Override
    public int remove(long cno) {
        return commentMapper.remove(cno);
    }

    @Override
    public int update(CommentVO cvo) {
        return commentMapper.update(cvo);
    }
}
