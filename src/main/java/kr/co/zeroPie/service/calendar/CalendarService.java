package kr.co.zeroPie.service.calendar;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.mapper.CalendarMapper;
import kr.co.zeroPie.repository.CalendarRepository;
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
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final ModelMapper modelMapper;
    private final CalendarMapper calendarMapper;

    // 스케쥴 보기
    public ResponseEntity<?> selectsSchedules(String stfNo) {
        log.info("스케쥴 보기");
        List<Calendar> calendars = calendarRepository.findByStfNo(stfNo);

        if(calendars.isEmpty()) {
            log.info("캘린더 비어있음 = 고로 아무것도 못찾음");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            log.info("캘린더 내용있음");
            return ResponseEntity.ok().body(calendars);
        }
    }

    // 캘린더 추가
    public ResponseEntity<?> insertCalendar(CalendarDTO calendarDTO) {
        log.info("캘린더 서비스 추가, calendarDTO : " + calendarDTO);

        Calendar calendar = modelMapper.map(calendarDTO, Calendar.class);
        calendarRepository.save(calendar);

        return ResponseEntity.ok().body(calendar);
    }

    // 이벤트 수정
    public ResponseEntity<?> modifyEvent(String id, CalendarDTO calendarDTO) {
        List<Calendar> calendars = calendarRepository.findById(id);
        if(calendars.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            calendarMapper.updateEvent(id, calendarDTO);
            return ResponseEntity.ok().build();
        }

    }

    // 이벤트 삭제
    @Transactional
    public ResponseEntity<?> deleteEvent(String id) {
        List<Calendar> calendars = calendarRepository.findById(id);
        if(calendars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
        }else{
            calendarRepository.deleteById(id);
            return ResponseEntity.ok().body(calendars);
        }
    }
}