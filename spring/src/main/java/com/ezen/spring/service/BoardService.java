package com.ezen.spring.service;

import com.ezen.spring.domain.BoardVO;

import java.util.List;

public interface BoardService {
    int register(BoardVO bvo);

    List<BoardVO> getList();
}
