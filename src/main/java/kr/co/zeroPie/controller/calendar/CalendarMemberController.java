package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.service.calendar.CalendarMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendarMembers")
public class CalendarMemberController {

    private final CalendarMemberService calendarMemberService;

    @GetMapping("/calendar/{calendarId}")
    public List<CalendarMemberDTO> getMembersByCalendarId(@PathVariable Long calendarId) {
        return calendarMemberService.getMembersByCalendarId(calendarId);
    }

    @PostMapping
    public CalendarMemberDTO addMember(@RequestBody CalendarMemberDTO calendarMemberDTO) {
        return calendarMemberService.addMember(calendarMemberDTO);
    }

    // 기타 캘린더 멤버 관련 엔드포인트
}
