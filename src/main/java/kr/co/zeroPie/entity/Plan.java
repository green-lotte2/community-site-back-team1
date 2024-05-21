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
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planNo;//요금제 번호
    
    private String planName;//요금제 이름
    
    private String planInfo;//요금제 설명
    
    private int planCost;//요금제 비용
    
    private int planDuration;//요금제 기간(ex.1달,3달....)

}
