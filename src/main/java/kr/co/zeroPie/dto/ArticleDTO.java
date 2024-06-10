package kr.co.zeroPie.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.zeroPie.entity.Article;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private String articleStatus;//게시글 상태

    private LocalDate articleRdate;//게시글 작성일


    private int articleHit;//게시글 조회수

    private int articleCateNo;//카테고리 번호(외래키)

    private int file;

    //private List<MultipartFile> image = new ArrayList<>();
    //private List<MultipartFile> files = new ArrayList<>();
    //private MultipartFile[] image;
    @JsonIgnore
    private List<MultipartFile> files;

    private List<FileDTO> fileList;


    // 추가된것
    private String writer;             //게시글 작성자
    private String articleThumb;       //게시글 썸네일

    public Article toEntity(){
        return Article.builder()
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
