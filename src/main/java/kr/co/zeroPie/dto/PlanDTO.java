package kr.co.zeroPie.dto;


import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDTO {

    private int planNo;//요금제 번호

    private String planName;//요금제 이름

    private String planInfo;//요금제 설명

    private int planCost;//요금제 비용

    private int planDuration;//요금제 기간(ex.1달,3달....)

}
