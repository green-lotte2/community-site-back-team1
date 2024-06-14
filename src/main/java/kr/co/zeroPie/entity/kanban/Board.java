package kr.co.zeroPie.entity.kanban;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class Board {

    @Id
    private String id;

    private String boardName;
    private int kanbanId;

    private String card;

    private int boardIndex;//칸반 번호

}
