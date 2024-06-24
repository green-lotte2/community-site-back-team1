package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stf")
public class Stf {
    //staff(사원)에 관한 테이블

    @Id
    private String stfNo;
    
    private String stfName;
    
    private String stfPass;
    
    private String stfPh;//사원 전화번호
    
    private int stfZip;//사원 우편번호
    
    private String stfAddr1;//주소
    
    private String stfAddr2;//상세주소

    private String stfEmail;

    @CreationTimestamp
    private LocalDate stfEnt;//입사일자

    private Date stfQuit;//퇴사일자

    private String stfImg;//사원 증명사진
    
    private String stfRole;//사원 권한

    private String stfStatus;//사원 상태

    private LocalDate stfBirth;

    private int dptNo;//부서번호

    private int rnkNo;//직급번호

    private int planStatusNo;//요금제 상태번호


}
