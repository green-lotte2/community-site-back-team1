package kr.co.zeroPie.entity.kanban;


import jakarta.persistence.*;
import lombok.*;

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


}
