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
@Table(name = "chatrecords")
public class ChatRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roomId;

    private String stfNo;

    private String stfName;

    private String message;

    @CreationTimestamp
    private LocalDateTime dateTime;

    private String img;

}
