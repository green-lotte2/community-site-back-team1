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
@Table(name = "task")
public class Task {

    @Id
    private String id;

    private String task;
    private String complete;
    private String card_id;

}
