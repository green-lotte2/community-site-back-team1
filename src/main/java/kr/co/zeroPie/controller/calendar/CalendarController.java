package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.service.calendar.CalendarService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping
    public List<CalendarDTO> getAllCalendars() {
        return calendarService.getAllCalendars();
    }

    @PostMapping
    public CalendarDTO createCalendar(@RequestBody CalendarDTO calendarDTO) {
        return calendarService.createCalendar(calendarDTO);
    }

    // 기타 캘린더 관련 엔드포인트
}
