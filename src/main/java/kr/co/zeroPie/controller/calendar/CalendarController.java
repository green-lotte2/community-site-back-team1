package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.service.calendar.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;


    // 캘린더 일정추가
    @PostMapping("/calendar/insert")
    public ResponseEntity<?> insertCalendar(@RequestBody CalendarDTO calendarDTO) {
        log.info("캘린더 컨트롤러 : " + calendarDTO);
        return calendarService.insertCalendar(calendarDTO);
    }


    // 캘린더 모든일정 불러오기
    @PostMapping("/calendar/selects")
    public ResponseEntity<?> selectsSchedules(@RequestBody Map<String, String> requestData) {
        String stfNo = requestData.get("stfNo");
        log.info("캘린더 가져오기 컨트롤러 : " + stfNo);

        return calendarService.selectsSchedules(stfNo);
    }


    // 캘린더 일정수정(id는 이벤트임, uid 아님)
    @PostMapping("/calendar/modify/{id}")
    public ResponseEntity<?> modifyEvent(@PathVariable("id") String id, @RequestBody CalendarDTO calendarDTO){
        log.info("수정 컨트롤러..1"+id);
        log.info("수정 컨트롤러..2"+calendarDTO);
        return calendarService.modifyEvent(id, calendarDTO);
    }

    // 특정 일정을 삭제
    @GetMapping("/calendar/delete")
    public ResponseEntity<?> deleteEvent(String id){
        log.info("삭제 컨트롤러" + id);
        return calendarService.deleteEvent(id);
    }

}