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
    public ResponseEntity<?> selectsSchedules(@RequestBody Map<String, Long> requestData) {
        Long calendarId = requestData.get("calendarId");
        log.info("캘린더 가져오기 컨트롤러, calendarId : " + calendarId);

        return eventsService.selectsSchedules(calendarId);
    }

    @PostMapping("/events/modify/{eventNo}")
    public ResponseEntity<?> modifyEvent(@PathVariable("eventNo") int eventNo, @RequestBody EventsDTO eventsDTO) {
        log.info("수정 컨트롤러..1" + eventNo);
        log.info("수정 컨트롤러..2" + eventsDTO);
        log.info("수정할 이벤트의 eventId: " + eventsDTO.getEventId()); // 수정 이벤트 ID 로그 추가
        return eventsService.modifyEvent(eventNo, eventsDTO);
    }

    // 특정 일정을 삭제
    @GetMapping("/events/delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("eventNo") int eventNo) {
        log.info("삭제 컨트롤러" + eventNo);
        return eventsService.deleteEvent(eventNo);
    }
}
