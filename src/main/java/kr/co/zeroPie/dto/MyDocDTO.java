package kr.co.zeroPie.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyDocDTO {
    private int myDocNo;
    private String stfNo;
    private int pno;
}
