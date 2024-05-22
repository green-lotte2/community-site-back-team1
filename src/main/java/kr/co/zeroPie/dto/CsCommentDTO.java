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
public class CsCommentDTO {

    private int csComNo;//고객센터 답변 번호(댓글 형태로 만들예정)

    private String csComContent;//답변 내용

    private LocalDate csComRdate;

    private int csNo;//고객센터 번호

    private String stfNo;//사원번호
}
