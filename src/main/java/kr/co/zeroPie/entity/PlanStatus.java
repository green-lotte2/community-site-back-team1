package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "planstatus")
public class PlanStatus {
    //요금제 사용에 관한 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planStatusNo;

    private int planNo;//요금제 번호

    private Date planSdate;//요금제 시작일

    private Date planEdate;//요금제 끝일
}
