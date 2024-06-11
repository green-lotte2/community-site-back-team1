package kr.co.zeroPie.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocDataDTO {
    private int dataNo;
    private String dataType;
    private String value;
    private int level;
}
