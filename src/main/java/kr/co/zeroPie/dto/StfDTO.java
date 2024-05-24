package kr.co.zeroPie.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    private String stfEnt;//입사일자

    private Date stfQuit;//퇴사일자

    private String stfImg;//사원 증명사진 이름

    private String stfRole;//사원 권한

    private int dptNo;//부서번호

    private int rnkNo;//직급 번호

    private int planStatusNo;//요금제 상태번호

    @JsonIgnore
    private MultipartFile thumbFile;//사원 증명사진이 실제로 들어있음
    private String oName;//증명사진 원래 이름
    private String sName;//바뀐 이름

    private String profile;

    private String strDptNo;//부서번호

    private String strRnkNo;//직급 번호


}
