package kr.co.zeroPie.service.calendar;

import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.CalendarMember;
import kr.co.zeroPie.repository.CalendarMemberRepository;
import kr.co.zeroPie.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final CalendarMemberRepository calendarMemberRepository;
    private final CalendarRepository calendarRepository;

    // 공유된 캘린더를 가져옴
    public List<CalendarDTO> getSharedCalendars(String stfNo) {
        return calendarRepository.findAllSharedCalendars(stfNo).stream()
                .map(Calendar::toDTO)
                .collect(Collectors.toList());
    }

    // 사용자 개인의 캘린더를 가져오거나 생성함
    public CalendarDTO getOrCreateUserCalendar(String userId, String username) {
        Optional<Calendar> existingCalendar = calendarRepository.findByOwnerStfNo(userId);
        if (existingCalendar.isPresent()) {
            return existingCalendar.get().toDTO();
        } else {
            Calendar newCalendar = Calendar.builder()
                    .title("나의 캘린더(" + username + ")")
                    .ownerStfNo(userId)
                    .build();
            Calendar savedCalendar = calendarRepository.save(newCalendar);
            return savedCalendar.toDTO();
        }
    }

    // 사용자의 모든 캘린더를 가져옴 (개인 캘린더 + 공유된 캘린더)
    public List<CalendarDTO> getAllUserCalendars(String userId, String username) {
        CalendarDTO userCalendar = getOrCreateUserCalendar(userId, username);
        List<CalendarDTO> sharedCalendars = getSharedCalendars(userId);
        sharedCalendars.add(0, userCalendar);  // 사용자 개인의 캘린더를 리스트의 첫 번째에 추가
        return sharedCalendars;
    }

    // 모든 캘린더를 가져옴
    public List<CalendarDTO> getAllCalendars() {
        return calendarRepository.findAll().stream()
                .map(Calendar::toDTO)
                .collect(Collectors.toList());
    }

    // 새로운 캘린더를 생성함
    public CalendarDTO createCalendar(CalendarDTO calendarDTO) {
        Calendar calendar = calendarRepository.save(calendarDTO.toEntity());

        // 캘린더 멤버 추가 로직
        if (calendarDTO.getMembers() != null) {
            for (CalendarMemberDTO memberDTO : calendarDTO.getMembers()) {
                if (!memberDTO.getStfNo().equals(calendar.getOwnerStfNo())) {
                    Optional<CalendarMember> existingMember = calendarMemberRepository.findByCalendarIdAndStfNo(calendar.getCalendarId(), memberDTO.getStfNo());
                    if (!existingMember.isPresent()) {
                        CalendarMember member = CalendarMember.builder()
                                .calendarId(calendar.getCalendarId())
                                .stfNo(memberDTO.getStfNo())
                                .build();
                        calendarMemberRepository.save(member);
                    }
                }
            }
        }

        // 캘린더 소유자도 멤버로 추가
        Optional<CalendarMember> existingOwnerMember = calendarMemberRepository.findByCalendarIdAndStfNo(calendar.getCalendarId(), calendar.getOwnerStfNo());
        if (!existingOwnerMember.isPresent()) {
            CalendarMember ownerMember = CalendarMember.builder()
                    .calendarId(calendar.getCalendarId())
                    .stfNo(calendar.getOwnerStfNo())
                    .build();
            calendarMemberRepository.save(ownerMember);
        }

        return calendar.toDTO();
    }
}