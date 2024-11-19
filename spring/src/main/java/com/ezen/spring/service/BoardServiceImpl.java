package com.ezen.spring.service;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.repository.BoardMapper;
import com.ezen.spring.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Transactional
    @Override
    public int register(BoardDTO bdto) {
        int isOk = boardMapper.register(bdto.getBvo());
        if(isOk > 0 && bdto.getFlist().size() > 0) {
            // 파일 저장
            // board의 bno 가져오기 => 가장 큰 bno
            long bno = boardMapper.getBno();
            for(FileVO fvo : bdto.getFlist()) {
                fvo.setBno(bno);
                isOk *= fileMapper.insertFile(fvo);
            }
        }
        return isOk;
    }

    @Override
    public List<BoardVO> getList(PagingVO pgvo) {
        return boardMapper.getList(pgvo);
    }

    public BoardDTO getDetail(long bno) {
        BoardDTO bdto = new BoardDTO(
                boardMapper.getDetail(bno),
                fileMapper.getFileList(bno));
        return bdto;
    }

    @Override
    public int update(BoardDTO bdto) {

        int isOk = boardMapper.update(bdto.getBvo());
        if(bdto.getFlist()== null) {
            return isOk;
        }
        if(isOk > 0 && bdto.getFlist().size() >0) {
            for(FileVO fvo : bdto.getFlist()) {
                fvo.setBno(bdto.getBvo().getBno());
                isOk *= fileMapper.insertFile(fvo);
            }
        }
        return isOk;
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return boardMapper.getTotalCount(pgvo);
    }

    @Override
    public int delete(long bno) {
        return boardMapper.delete(bno);
    }

    @Override
    public int removeFile(String uuid) {
        return fileMapper.removeFile(uuid);
    }

    @Override
    public FileVO getFile(String uuid) {
        return fileMapper.getFile(uuid);
    }

}
