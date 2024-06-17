package kr.co.zeroPie.dto;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRecordsDTO {

    private int id;

    private String roomId;

    private String stfNo;

    private String stfName;

    private String message;

    private LocalDateTime dateTime;

    private String Img;
}
