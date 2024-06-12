package kr.co.zeroPie.dto.kanban;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private String id;
    private String task;
    private String complete;
    private String card_id;
}
