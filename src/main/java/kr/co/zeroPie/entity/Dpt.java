package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dpt")
public class Dpt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dptNo;//부서 번호

    private String dptName;//부서 이름
}
