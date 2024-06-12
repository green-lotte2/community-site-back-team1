package kr.co.zeroPie.dto.kanban;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO {
    private String id;
    private String title;
    private String board_id;
}
