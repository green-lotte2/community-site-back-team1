package kr.co.zeroPie.dto;



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
public class ToDoDTO {

    private int todoNo;//할일 리스트 번호

    private String todoStatus;//할일 체크리스트에 체크했는지 확인

    private String todoContent;//할일 내용

    private LocalDateTime todoDate;

    private String stfNo;//사원번호
}
