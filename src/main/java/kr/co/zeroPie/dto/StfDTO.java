package kr.co.zeroPie.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StfDTO {

    private String stfNo;

    private String stfName;

    private String stfPass;

    private String stfPh;//사원 전화번호

    private int stfZip;//사원 우편번호

    private String stfAddr1;//주소

    private String stfAddr2;//상세주소

    private String stfEmail;

    private Date stfEnt;//입사일자

    private Date stfQuit;//퇴사일자

    private String stfImg;//사원 증명사진

    private int dptNo;//부서번호

    private int rnkNO;//직급 번호

    private int planStatusNo;//요금제 상태번호
}
