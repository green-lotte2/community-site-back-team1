package kr.co.zeroPie.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticlePageResponseDTO {

    private List<ArticleDTO> dtoList;
    private int articleCateNo;
    private String type;
    private String keyword;

    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;


    @Builder
    public ArticlePageResponseDTO(ArticlePageRequestDTO articlePageRequestDTO, List<ArticleDTO> dtoList, int total){
        this.articleCateNo = articlePageRequestDTO.getArticleCateNo();
        this.type = articlePageRequestDTO.getType();
        this.keyword = articlePageRequestDTO.getKeyword();

        this.pg = articlePageRequestDTO.getPg();
        this.size = articlePageRequestDTO.getSize();
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