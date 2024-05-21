package kr.co.zeroPie.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private int commentNo;//댓글 번호

    private int articleNo;//게시글 번호

    private String stfNo;//사원번호(글쓴이)

    private String commentCnt;//댓글 내용

    private LocalDateTime commentRdate;//작성일자(오늘을 기준)
}
