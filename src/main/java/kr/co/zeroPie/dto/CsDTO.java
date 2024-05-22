package kr.co.zeroPie.dto;


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
public class CsDTO {

    private int csNo;//고객센터 게시글 번호

    private String csCate;//카테고리 번호(외래키)

    private String csTitle;

    private String csContent;//고객센터 게시글 내용

    private LocalDate csRdate;

    private int csHit;//조회수

    private int csReply;//답변이 달린 수 : 답변이 하나라도 달리면 답변완료로 바꾸어 주기

    private String stfNo;//사원번호
}
