package kr.co.zeroPie.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EventsDTO {
    private int eventNo;                // 일정 고유 번호
    private String stfNo;               // 사용자 ID
    private Long calendarId;            // 캘린더 ID
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
}
