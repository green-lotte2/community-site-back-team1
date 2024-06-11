package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.Stf;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarDTO {
    private Long calendarId;
    private String title;
    private String ownerStfNo;

    public Calendar toEntity() {
        Stf owner = new Stf();
        owner.setStfNo(ownerStfNo);
        return Calendar.builder()
                .calendarId(calendarId)
                .title(title)
                .owner(owner)
                .build();
    }
}
