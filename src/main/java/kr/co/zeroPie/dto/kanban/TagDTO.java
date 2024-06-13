package kr.co.zeroPie.dto.kanban;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {
    private String id;
    private String tagName;
    private String color;
    //private String card_id;
}
