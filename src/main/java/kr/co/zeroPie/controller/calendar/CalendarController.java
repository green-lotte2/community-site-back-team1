package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    // 사용자가 공유된 모든 캘린더를 가져옴
    @GetMapping("/shared/{stfNo}")
    public List<CalendarDTO> getSharedCalendars(@PathVariable String stfNo) {
        return calendarService.getSharedCalendars(stfNo);
    }

    // 사용자 개인의 캘린더를 가져오거나 생성함
    @GetMapping("/user/{userId}")
    public CalendarDTO getUserCalendar(@PathVariable String userId, @RequestParam String username) {
        return calendarService.getOrCreateUserCalendar(userId, username);
    }

    // 사용자의 모든 캘린더를 가져옴 (개인 캘린더 + 공유된 캘린더)
    @GetMapping("/all/{userId}")
    public List<CalendarDTO> getAllUserCalendars(@PathVariable String userId, @RequestParam String username) {
        return calendarService.getAllUserCalendars(userId, username);
    }

    // 모든 캘린더를 가져옴 (관리자용)
    @GetMapping
    public List<CalendarDTO> getAllCalendars() {
        return calendarService.getAllCalendars();
    }

    // 새로운 캘린더를 생성함
    @PostMapping
    public CalendarDTO createCalendar(@RequestBody CalendarDTO calendarDTO) {
        return calendarService.createCalendar(calendarDTO);
    }
}
