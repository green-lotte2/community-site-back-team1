package kr.co.zeroPie.dto;


import lombok.*;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class PlanStatusDTO {

    private int planStatusNo;

    private int planNo;//요금제 번호

    private Date planSdate;//요금제 시작일

    private Date planEdate;//요금제 끝일
}
