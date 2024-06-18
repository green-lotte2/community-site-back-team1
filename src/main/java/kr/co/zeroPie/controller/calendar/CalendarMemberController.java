package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.service.calendar.CalendarMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendarMembers")
public class CalendarMemberController {

    private final CalendarMemberService calendarMemberService;

    // 사용자의 모든 캘린더 멤버십(어떤 캘린더에 속해있냐 여부)을 가져옴
    @GetMapping("/user/{stfNo}")
    public List<CalendarMemberDTO> getMembersByStfNo(@PathVariable String stfNo) {
        return calendarMemberService.getMembersByStfNo(stfNo);
    }

    // 특정 캘린더에 속한 모든 멤버를 가져옴
    @GetMapping("/calendar/{calendarId}")
    public List<CalendarMemberDTO> getMembersByCalendarId(@PathVariable Long calendarId) {
        return calendarMemberService.getMembersByCalendarId(calendarId);
    }

    // 새로운 멤버를 캘린더에 추가
    @PostMapping
    public List<CalendarMemberDTO> addMembers(@RequestBody List<CalendarMemberDTO> calendarMemberDTOs) {
        return calendarMemberService.addMembers(calendarMemberDTOs);
    }

    // 캘린더에서 멤버를 제거
    @DeleteMapping("/leave")
    public void leaveCalendar(@RequestBody CalendarMemberDTO calendarMemberDTO) {
        calendarMemberService.leaveCalendar(calendarMemberDTO);
    }
}
