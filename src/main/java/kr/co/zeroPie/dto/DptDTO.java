package kr.co.zeroPie.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DptDTO {

    private int dptNo;//부서 번호

    private String dptName;//부서 이름

    private String dptCode;//부서 코드

    private String iconName; //부서 아이콘

}
