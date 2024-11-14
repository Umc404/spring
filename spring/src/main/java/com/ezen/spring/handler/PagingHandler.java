package com.ezen.spring.handler;

import com.ezen.spring.domain.PagingVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {
    private int startPage;
    private int endPage;
    private int realEndPage;
    private boolean prev, next;

    private int totalCount;
    private PagingVO pgvo;

    public PagingHandler(PagingVO pgvo, int totalCount) {
        this.pgvo = pgvo;
        this.totalCount = totalCount;

        // 10 20 30
        // ( 현재 페이지번호 / 10 ) 올림 => * 10
        this.endPage = (int)Math.ceil(pgvo.getPageNo() / 10.0) * 10;
        this.startPage = endPage - 9;

        this.realEndPage = (int)Math.ceil(totalCount / (double)pgvo.getQty());

        if(endPage > realEndPage) {
            this.endPage = realEndPage;
        }

        this.prev = this.startPage > 1;   // 1 11 21
        this.next = this.endPage < this.realEndPage;


    }
}
