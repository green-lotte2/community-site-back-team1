package kr.co.zeroPie.dto.kanban;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanDTO {

    private int kanbanId;//칸반 번호

    private String kanbanName;//칸반 이름
    private String kanbanInfo;
    private String kanbanStf;
}
