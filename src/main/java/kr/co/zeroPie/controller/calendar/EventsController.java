package kr.co.zeroPie.controller.calendar;

import kr.co.zeroPie.dto.EventsDTO;
import kr.co.zeroPie.service.calendar.EventsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EventsController {

    private final EventsService eventsService;

    // 캘린더 일정추가
    @PostMapping("/events/insert")
    public ResponseEntity<?> insertEvents(@RequestBody EventsDTO eventsDTO) {
        log.info("캘린더 컨트롤러 : " + eventsDTO);
        EventsDTO createdEvent = eventsService.insertEvents(eventsDTO);
        return ResponseEntity.ok(createdEvent);
    }

    // 캘린더 모든일정 불러오기
    @PostMapping("/events/selects")
    public ResponseEntity<?> selectsSchedules(@RequestBody Map<String, String> requestData) {
        String stfNo = requestData.get("stfNo");
        Long calendarId = Long.parseLong(requestData.get("calendarId")); // 추가된 부분
        log.info("캘린더 가져오기 컨트롤러 : " + stfNo + ", calendarId : " + calendarId);

        return eventsService.selectsSchedules(stfNo, calendarId);
    }

    // 캘린더 일정수정
    @PostMapping("/events/modify/{eventId}")
    public ResponseEntity<?> modifyEvent(@PathVariable("eventId") String eventId, @RequestBody EventsDTO eventsDTO) {
        log.info("수정 컨트롤러..1" + eventId);
        log.info("수정 컨트롤러..2" + eventsDTO);
        return eventsService.modifyEvent(eventId, eventsDTO);
    }

    // 특정 일정을 삭제
    @GetMapping("/events/delete")
    public ResponseEntity<?> deleteEvent(String eventId) {
        log.info("삭제 컨트롤러" + eventId);
        return eventsService.deleteEvent(eventId);
    }

}
