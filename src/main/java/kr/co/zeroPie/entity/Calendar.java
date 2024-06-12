package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.CalendarDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;
    private String title;
    private String ownerStfNo;

    public CalendarDTO toDTO() {
        return CalendarDTO.builder()
                .calendarId(calendarId)
                .title(title)
                .ownerStfNo(ownerStfNo)
                .build();
    }
}
