package kr.co.zeroPie.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rnk")
public class Rnk {
    //사원직급에 관한 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rnkNo;

    private String rnkName;//직급이름

}
