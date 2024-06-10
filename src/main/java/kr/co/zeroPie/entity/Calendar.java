package kr.co.zeroPie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.zeroPie.dto.CalendarDTO;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@ToString
@Table(name = "`calendar`")
public class Calendar {
    @Id
    private int calNo;                  // 일정 고유 번호
    private String stfNo;               // 사용자 ID (loginSlice.userId)
    private String calendarId;          // 캘린더 ID (options.calendars의 id > 캘린더 종류맞음(본인 캘린더, 초대하는 캘린더 등등 구별용))
    private String id;                  // 이벤트 ID
    private String title;               // 일정 제목
    private LocalDateTime start;    // 일정 시작 시간
    private LocalDateTime end;      // 일정 종료 시간
    private String location;            // 일정 위치
    private String state;               // 일정 상태
    private String backgroundColor;     // 배경 색상 (getRandomColor 함수로 설정된 값)
    private String color;               // 글자 색상
    private String isAllDay;            // 하루 종일 여부
    private String isReadOnly;          // 읽기 전용 여부

    public CalendarDTO toDTO(){
        return CalendarDTO.builder()
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
