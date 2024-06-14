package kr.co.zeroPie.entity.kanban;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_overview")
public class BoardOverview {
    @Id
    private String id;
    private String boardName;
    private int kanbanId;
    private String kanbanName;
    private String kanbanInfo;
    private String kanbanStf;
    private String card;

}
