package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.CalendarMember;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CalendarMemberDTO {
    private Long calMemId;
    private Long calendarId;
    private String stfNo;

    public CalendarMember toEntity() {
        return CalendarMember.builder()
                .calMemId(calMemId)
                .calendarId(calendarId)
                .stfNo(stfNo)
                .build();
    }
}
