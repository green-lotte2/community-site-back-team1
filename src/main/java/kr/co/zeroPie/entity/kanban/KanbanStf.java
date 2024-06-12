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
@Table(name = "kanbanStf")
public class KanbanStf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kanbanStfNo;//칸반 번호
    private int kanbanId;//칸반 번호
    private String stfNo;//유저 번호

}
