package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.CalendarMemberDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "calendarMembers")
public class CalendarMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calMemId;

    private Long calendarId;
    private String stfNo;

    public CalendarMemberDTO toDTO() {
        return CalendarMemberDTO.builder()
                .calMemId(calMemId)
                .calendarId(calendarId)
                .stfNo(stfNo)
                .build();
    }
}
