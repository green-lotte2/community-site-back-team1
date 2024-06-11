package kr.co.zeroPie.dto;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocDTO {
    private int docNo;
    private String id;
    private String type;
    private int dataNo;
    
    // 추가 필드
    private DocDataDTO data;
}
