package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.Calendar;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarDTO {
    private int calNo;
    private String stfNo;
    private String calendarId;
    private String id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String state;
    private String backgroundColor;
    private String color;
    private String isAllDay;
    private String isReadOnly;

    public Calendar toEntity(){
        return Calendar.builder()
                .calNo(calNo)
                .stfNo(stfNo)
                .calendarId(calendarId)
                .id(id)
                .title(title)
                .start(start)
                .end(end)
                .location(location)
                .state(state)
                .backgroundColor(backgroundColor)
                .color(color)
                .isAllDay(isAllDay)
                .isReadOnly(isReadOnly)
                .build();
    }
}
