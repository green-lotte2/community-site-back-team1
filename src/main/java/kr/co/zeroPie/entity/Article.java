package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.ArticleDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleNo;//게시글 번호

    private String stfNo;//사원번호

    private String articleTitle;//게시글 제목

    private String articleCnt;//게시글 내용

    @CreationTimestamp
    private LocalDateTime articleRdate;//게시글 작성일

    private int articleHit;//게시글 조회수

    private int articleCateNo;//카테고리 번호(외래키)

    private String writer;             //게시글 작성자

    private String articleThumb;       //게시글 썸네일

    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .articleNo(articleNo)
                .stfNo(stfNo)
                .articleTitle(articleTitle)
                .articleCnt(articleCnt)
                .articleRdate(articleRdate)
                .articleHit(articleHit)
                .articleCateNo(articleCateNo)
                .writer(writer)
                .articleThumb(articleThumb)
                .build();
    }



}
