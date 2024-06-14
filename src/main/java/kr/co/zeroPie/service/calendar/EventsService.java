package kr.co.zeroPie.service.calendar;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.EventsDTO;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.Events;
import kr.co.zeroPie.repository.CalendarMemberRepository;
import kr.co.zeroPie.repository.CalendarRepository;
import kr.co.zeroPie.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventsService {
    private final EventsRepository eventsRepository;
    private final CalendarMemberRepository calendarMemberRepository;
    private final ModelMapper modelMapper;
    private final CalendarRepository calendarRepository;

    // 스케쥴 보기
    public ResponseEntity<?> selectsSchedules(Long calendarId) {
        log.info("스케쥴 보기");
        List<Events> events = eventsRepository.findByCalendarId(calendarId);

        if (events.isEmpty()) {
            log.info("캘린더 비어있음 = 고로 아무것도 못찾음");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        } else {
            log.info("캘린더 내용있음");
            return ResponseEntity.ok().body(events);
        }
    }

    // 캘린더 추가
    public EventsDTO insertEvents(EventsDTO eventsDTO) {
        log.info("캘린더 서비스 추가, eventsDTO : " + eventsDTO);

        if (eventsDTO.getEventId() == null || eventsDTO.getEventId().isEmpty()) {
            eventsDTO.setEventId(UUID.randomUUID().toString());  // 고유 ID 생성
        }

        Events events = modelMapper.map(eventsDTO, Events.class);
        Events savedEvent = eventsRepository.save(events);

        return savedEvent.toDTO();
    }

    // 이벤트 수정
    public ResponseEntity<?> modifyEvent(int eventNo, EventsDTO eventsDTO) {
        Events event = eventsRepository.findById(eventNo).orElse(null);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }

        // 캘린더 ID 유효성 검사
        Optional<Calendar> calendar = calendarRepository.findById(eventsDTO.getCalendarId());
        if (!calendar.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid calendarId");
        }

        // eventId 유지 또는 설정
        if(eventsDTO.getEventId() == null) {
            eventsDTO.setEventId(event.getEventId());
        }

        modelMapper.map(eventsDTO, event);
        event.setStfNo(eventsDTO.getStfNo()); // 수정한 사람의 stfNo로 업데이트
        eventsRepository.save(event);
        return ResponseEntity.ok(event.toDTO());
    }


    // 이벤트 삭제
    @Transactional
    public ResponseEntity<?> deleteEvent(int eventNo) {
        Events event = eventsRepository.findById(eventNo).orElse(null);
        if (event == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        } else {
            eventsRepository.delete(event);
            return ResponseEntity.ok().build();
        }
    }
}