package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.Calendar;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CalendarDTO {
    private Long calendarId;
    private String title;
    private String ownerStfNo;
    private List<CalendarMemberDTO> members;

    public Calendar toEntity() {
        return Calendar.builder()
                .calendarId(calendarId)
                .title(title)
                .ownerStfNo(ownerStfNo)
                .build();
    }
}
