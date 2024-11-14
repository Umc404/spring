package com.ezen.spring.repository;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int register(BoardVO bvo);

    BoardVO getDetail(long bno);

    int update(BoardVO bvo);

    List<BoardVO> getList(PagingVO pgvo);

    int getTotalCount(PagingVO pgvo);
}
