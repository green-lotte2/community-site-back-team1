package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNo;//댓글 번호

    private int articleNo;//게시글 번호

    private String stfNo;//사원번호(글쓴이)

    private String commentCnt;//댓글 내용

    @CreationTimestamp
    private LocalDateTime commentRdate;//작성일자(오늘을 기준)

}
