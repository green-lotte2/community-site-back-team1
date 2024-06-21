package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cs")
public class Cs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int csNo;//고객센터 게시글 번호

    private String csCate;//카테고리 번호(외래키)

    private String csTitle;

    private String csContent;//고객센터 게시글 내용

    @CreationTimestamp
    private LocalDateTime csRdate;//DateTime으로 수정하기

    private int csHit;//조회수

    private int csReply;//답변이 달린 수 : 답변이 하나라도 달리면 답변완료로 바꾸어 주기

    private String stfNo;//사원번호

    private String stfName;

    private String secret;//전체글인지, 비밀글인지

    // 생성 시간을 초기화하는 메서드
    @PrePersist
    protected void onCreate() {
        csRdate = LocalDateTime.now();
    }
}
