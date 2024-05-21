package kr.co.zeroPie.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {

    private int articleNo;//게시글 번호

    private String stfNo;//사원번호

    private String articleTitle;//게시글 제목

    private String articleCnt;//게시글 내용

    private String articleRdate;//게시글 작성일

    private int articleHit;//게시글 조회수

    private int articleCateNo;//카테고리 번호(외래키)
}
