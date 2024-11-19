package com.ezen.spring.repository;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    int post(CommentVO cvo);

    int getTotalCount(long bno);

    List<CommentVO> getList(@Param("pgvo") PagingVO pgvo, @Param("bno") long bno);

    int remove(long cno);

    int update(CommentVO cvo);
}
