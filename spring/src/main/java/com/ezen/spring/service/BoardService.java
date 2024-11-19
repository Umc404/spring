package com.ezen.spring.service;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;

import java.util.List;

public interface BoardService {
    int register(BoardDTO bdto);

    List<BoardVO> getList(PagingVO pgvo);

     BoardDTO getDetail(long bno);

    int update(BoardDTO bdto);

    int getTotalCount(PagingVO pgvo);

    int delete(long bno);

    int removeFile(String uuid);

    FileVO getFile(String uuid);
}
