package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.EventsDTO;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "events")
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventNo;                // 일정 고유 번호
    private String stfNo;               // 사용자 ID (loginSlice.userId)
    private String eventId;             // 이벤트 ID
    private String title;               // 일정 제목
    private LocalDateTime start;        // 일정 시작 시간
    private LocalDateTime end;          // 일정 종료 시간
    private String location;            // 일정 위치
    private String state;               // 일정 상태
    private String backgroundColor;     // 배경 색상
    private String color;               // 글자 색상
    private boolean isAllDay;           // 하루 종일 여부
    private boolean isReadOnly;         // 읽기 전용 여부

    private Long calendarId;            // 외래 키로 캘린더 ID

    public EventsDTO toDTO() {
        return EventsDTO.builder()
                .eventNo(eventNo)
                .stfNo(stfNo)
                .calendarId(calendarId)
                .eventId(eventId)
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

    // 수동으로 setter 메서드 추가
    public void setAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}
