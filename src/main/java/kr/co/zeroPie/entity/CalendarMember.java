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

    @ManyToOne
    @JoinColumn(name = "calendarId")
    private Calendar calendar;

    @ManyToOne
    @JoinColumn(name = "stfNo")
    private Stf stf;

    public CalendarMemberDTO toDTO() {
        return CalendarMemberDTO.builder()
                .calMemId(calMemId)
                .calendarId(calendar.getCalendarId())
                .stfNo(stf.getStfNo())
                .build();
    }
}
