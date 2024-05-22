package kr.co.zeroPie.entity;


import jakarta.persistence.*;
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
@Table(name = "cscomment")
public class CsComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int csComNo;//고객센터 답변 번호(댓글 형태로 만들예정)

    private String csComContent;//답변 내용

    @CreationTimestamp
    private LocalDate csComRdate;
    
    private int csNo;//고객센터 번호
    
    private String stfNo;//사원번호

}
