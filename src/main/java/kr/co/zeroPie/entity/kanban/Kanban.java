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
@Table(name = "kanban")
public class Kanban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kanbanId;//칸반 번호

    private String kanbanName;//칸반 이름

    private String kanbanInfo;
    private String kanbanStf;


}
