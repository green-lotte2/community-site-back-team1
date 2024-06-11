package kr.co.zeroPie.dto;

import lombok.*;

import java.util.List;

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

    private List<StfDTO> member ; //부서 유저

    public DptDTO(int dptNo, String dptName, String dptCode, String iconName) {
    }
}
