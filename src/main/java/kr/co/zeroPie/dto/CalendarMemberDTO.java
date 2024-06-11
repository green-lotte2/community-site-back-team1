package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.CalendarMember;
import kr.co.zeroPie.entity.Stf;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarMemberDTO {
    private Long calMemId;
    private Long calendarId;
    private String stfNo;

    public CalendarMember toEntity() {
        Calendar calendar = new Calendar();
        calendar.setCalendarId(calendarId);
        Stf stf = new Stf();
        stf.setStfNo(stfNo);

        return CalendarMember.builder()
                .calMemId(calMemId)
                .calendar(calendar)
                .stf(stf)
                .build();
    }
}
