package kr.co.zeroPie.service.calendar;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.EventsDTO;
import kr.co.zeroPie.entity.Events;
import kr.co.zeroPie.mapper.EventsMapper;
import kr.co.zeroPie.repository.EventsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventsService {
    private final EventsRepository eventsRepository;
    private final ModelMapper modelMapper;
    private final EventsMapper eventsMapper;

    // 스케쥴 보기
    public ResponseEntity<?> selectsSchedules(String stfNo, Long calendarId) {
        log.info("스케쥴 보기");
        List<Events> events = eventsRepository.findByStfNoAndCalendarId(stfNo, calendarId);

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

        Events events = modelMapper.map(eventsDTO, Events.class);
        Events savedEvent = eventsRepository.save(events);

        return savedEvent.toDTO();
    }

    // 이벤트 수정
    public ResponseEntity<?> modifyEvent(String eventId, EventsDTO eventsDTO) {
        List<Events> events = eventsRepository.findByEventId(eventId);
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        } else {
            eventsMapper.updateEvent(eventId, eventsDTO);
            return ResponseEntity.ok().build();
        }
    }

    // 이벤트 삭제
    @Transactional
    public ResponseEntity<?> deleteEvent(String eventId) {
        List<Events> events = eventsRepository.findByEventId(eventId);
        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        } else {
            eventsRepository.deleteByEventId(eventId);
            return ResponseEntity.ok().body(events);
        }
    }
}