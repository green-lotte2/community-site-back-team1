package kr.co.zeroPie.service.calendar;

import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.entity.CalendarMember;
import kr.co.zeroPie.repository.CalendarMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarMemberService {

    private final CalendarMemberRepository calendarMemberRepository;

    // 특정 사용자가 속한 모든 캘린더 멤버십 정보 가져옴
    public List<CalendarMemberDTO> getMembersByStfNo(String stfNo) {  // stfNo를 String으로 변경
        return calendarMemberRepository.findByStfNo(stfNo).stream()
                .map(CalendarMember::toDTO)
                .collect(Collectors.toList());
    }

    // 특정 캘린더에 속한 모든 멤버 정보를 가져옵니다.
    public List<CalendarMemberDTO> getMembersByCalendarId(Long calendarId) {
        return calendarMemberRepository.findByCalendarId(calendarId).stream()
                .map(CalendarMember::toDTO)
                .collect(Collectors.toList());
    }

    // 새로운 멤버를 캘린더에 추가
    public List<CalendarMemberDTO> addMembers(List<CalendarMemberDTO> calendarMemberDTOs) {
        List<CalendarMember> calendarMembers = calendarMemberDTOs.stream()
                .map(CalendarMemberDTO::toEntity)
                .collect(Collectors.toList());

        List<CalendarMember> savedMembers = calendarMemberRepository.saveAll(calendarMembers);

        return savedMembers.stream()
                .map(CalendarMember::toDTO)
                .collect(Collectors.toList());
    }

    // 특정 멤버를 캘린더에서 제거합니다.
    public void leaveCalendar(CalendarMemberDTO calendarMemberDTO) {
        CalendarMember calendarMember = calendarMemberRepository.findByCalendarIdAndStfNo(
                calendarMemberDTO.getCalendarId(), calendarMemberDTO.getStfNo()
        ).orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));
        calendarMemberRepository.delete(calendarMember);
    }
}
