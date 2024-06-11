package kr.co.zeroPie.service.calendar;

import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.entity.CalendarMember;
import kr.co.zeroPie.repository.CalendarMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarMemberService {

    private final CalendarMemberRepository calendarMemberRepository;

    public List<CalendarMemberDTO> getMembersByCalendarId(Long calendarId) {
        return calendarMemberRepository.findAll().stream()
                .filter(member -> member.getCalendar().getCalendarId().equals(calendarId))
                .map(CalendarMember::toDTO)
                .collect(Collectors.toList());
    }

    public CalendarMemberDTO addMember(CalendarMemberDTO calendarMemberDTO) {
        CalendarMember calendarMember = calendarMemberRepository.save(calendarMemberDTO.toEntity());
        return calendarMember.toDTO();
    }

    // 기타 캘린더 멤버 관련 비즈니스 로직
}
