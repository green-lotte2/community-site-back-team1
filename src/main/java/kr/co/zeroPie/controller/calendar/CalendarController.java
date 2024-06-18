package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    // 사용자의 모든 캘린더를 가져옴 (개인 캘린더 + 공유된 캘린더)
    @GetMapping("/all/{userId}")
    public List<CalendarDTO> getAllUserCalendars(@PathVariable String userId, @RequestParam String username) {
        List<CalendarDTO> calendars = calendarService.getAllUserCalendars(userId, username);
        log.info("Returning all calendars for user {}: {}", userId, calendars);
        return calendars;
    }

    // 새로운 캘린더를 생성함
    @PostMapping
    public CalendarDTO createCalendar(@RequestBody CalendarDTO calendarDTO) {
        CalendarDTO createdCalendar = calendarService.createNewCalendar(calendarDTO);
        log.info("Created calendar: {}", createdCalendar);
        return createdCalendar;
    }

    // 캘린더 삭제
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<Void> deleteCalendar(@PathVariable Long calendarId) {
        calendarService.deleteCalendar(calendarId);
        return ResponseEntity.noContent().build();
    }
}
