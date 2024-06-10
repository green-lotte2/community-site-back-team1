package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
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

    // 기본 생성자
    public PlanStatus() {

    }

    // 생성자
    public PlanStatus(int planNo) {
        this.planNo = planNo;
        this.planSdate = new Date(); // 현재 날짜 설정
        setPlanEdate(); // planEdate 설정
    }


    // planSdate에 30일을 더한 값을 planEdate에 설정하는 메서드
    public void setPlanEdate() {
        if (planSdate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(planSdate);
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            planEdate = calendar.getTime();
        }
    }
}
