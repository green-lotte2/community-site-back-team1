package kr.co.zeroPie.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponseDTO<T> {

    private List<T> dtoList;
    private String cate;
    private String type;
    private String keyword;
    private String csReply;
    private String latest;
    private String hit;

    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    private String stfStatus;
    private String stfRole;
    private int rnkNo;
    private int dptNo;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total) {
        this.cate = pageRequestDTO.getCsCate();
        this.csReply = pageRequestDTO.getCsReply();
        this.type = pageRequestDTO.getType();
        this.keyword = pageRequestDTO.getKeyword();
        this.latest = pageRequestDTO.getLatest();
        this.hit = pageRequestDTO.getHit();


        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int) (Math.ceil(total / (double) size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}