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
@Table(name = "card")
public class Card {

    @Id
    private String id;

    private String title;
    private String board_id;


}
