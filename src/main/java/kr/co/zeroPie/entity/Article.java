package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.dto.StfDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private int articleNo;              //게시글 번호

    private String stfNo;               //사원번호

    private String articleTitle;        //게시글 제목

    private String articleCnt;          //게시글 내용

    private String articleStatus;    // 게시글 상태

    @CreationTimestamp
    private LocalDate articleRdate;     //게시글 작성일

    @ColumnDefault("0")
    private int articleHit;             //게시글 조회수

    private int articleCateNo;          //카테고리 번호(외래키)

    private String writer;              //게시글 작성자

    private String articleThumb;        //게시글 썸네일

    @Builder.Default
    private int file = 0;

    @OneToMany(mappedBy = "articleNo")
    private List<File> fileList;

    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .articleNo(articleNo)
                .stfNo(stfNo)
                .articleTitle(articleTitle)
                .articleCnt(articleCnt)
                .articleStatus(articleStatus)
                .articleRdate(articleRdate)
                .articleHit(articleHit)
                .articleCateNo(articleCateNo)
                .writer(writer)
                .file(file)
                .articleThumb(articleThumb)
                .build();
    }
}