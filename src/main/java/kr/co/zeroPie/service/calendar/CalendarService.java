package kr.co.zeroPie.service.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public List<CalendarDTO> getAllCalendars() {
        return calendarRepository.findAll().stream()
                .map(Calendar::toDTO)
                .collect(Collectors.toList());
    }

    public CalendarDTO createCalendar(CalendarDTO calendarDTO) {
        Calendar calendar = calendarRepository.save(calendarDTO.toEntity());
        return calendar.toDTO();
    }

    // 기타 캘린더 관련 비즈니스 로직
}
