package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "planorder")
public class PlanOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    private int planStatusNo;//플랜의 상태를 저장

    private String stfName;//주문자 명

    private String stfEmail;//주문자 이메일

    private String stfPh;//주문자 연락처

    private String paymentMethod;//결제정보

    private int cost;//결제 금액

    @Transient
    private int planNo;//선택한 플랜 번호

}
