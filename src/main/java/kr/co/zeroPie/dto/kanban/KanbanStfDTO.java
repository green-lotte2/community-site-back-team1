package kr.co.zeroPie.dto.kanban;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanStfDTO {

    private int kanbanStfNo;//칸반 번호
    private int kanbanId;//칸반 번호
    private String stfNo;//유저 번호

}
