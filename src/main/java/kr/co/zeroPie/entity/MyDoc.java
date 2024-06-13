package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "myDoc")
public class MyDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myDocNo;
    private String stfNo;
    private int pno;
}
