package kr.co.zeroPie.service.calendar;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.CalendarDTO;
import kr.co.zeroPie.dto.CalendarMemberDTO;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.CalendarMember;
import kr.co.zeroPie.repository.CalendarMemberRepository;
import kr.co.zeroPie.repository.CalendarRepository;
import kr.co.zeroPie.repository.EventsRepository;
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
    private final EventsRepository eventsRepository;

    // 사용자 개인의 캘린더를 가져오거나 생성함
    public CalendarDTO getOrCreateUserCalendar(String userId, String username) {
        List<Calendar> existingCalendars = calendarRepository.findByOwnerStfNo(userId);
        if (!existingCalendars.isEmpty()) {
            CalendarDTO calendarDTO = existingCalendars.get(0).toDTO();
            calendarDTO.setMembers(getMembersByCalendarId(calendarDTO.getCalendarId()));
            return calendarDTO;
        } else {
            Calendar newCalendar = Calendar.builder()
                    .title("나의 캘린더(" + username + ")")
                    .ownerStfNo(userId)
                    .build();
            Calendar savedCalendar = calendarRepository.save(newCalendar);
            CalendarDTO calendarDTO = savedCalendar.toDTO();
            calendarDTO.setMembers(getMembersByCalendarId(savedCalendar.getCalendarId()));
            return calendarDTO;
        }
    }

    // 사용자의 모든 캘린더를 가져옴 (개인 캘린더 + 공유된 캘린더)
    public List<CalendarDTO> getAllUserCalendars(String userId, String username) {
        CalendarDTO userCalendar = getOrCreateUserCalendar(userId, username);
        List<CalendarDTO> sharedCalendars = getSharedCalendars(userId);
        sharedCalendars.add(0, userCalendar);  // 사용자 개인의 캘린더를 리스트의 첫 번째에 추가
        return sharedCalendars;
    }

    // 새로운 캘린더를 생성함
    public CalendarDTO createNewCalendar(CalendarDTO calendarDTO) {
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

        CalendarDTO createdCalendarDTO = calendar.toDTO();
        createdCalendarDTO.setMembers(getMembersByCalendarId(calendar.getCalendarId()));

        return createdCalendarDTO;
    }

    // 특정 캘린더의 멤버를 가져오는 메소드
    private List<CalendarMemberDTO> getMembersByCalendarId(Long calendarId) {
        return calendarMemberRepository.findByCalendarId(calendarId).stream()
                .map(CalendarMember::toDTO)
                .collect(Collectors.toList());
    }

    // 공유된 캘린더를 가져옴
    private List<CalendarDTO> getSharedCalendars(String stfNo) {
        return calendarRepository.findAllSharedCalendars(stfNo).stream()
                .map(calendar -> {
                    CalendarDTO calendarDTO = calendar.toDTO();
                    calendarDTO.setMembers(getMembersByCalendarId(calendar.getCalendarId()));
                    return calendarDTO;
                })
                .collect(Collectors.toList());
    }

    // 캘린더 삭제
    @Transactional
    public void deleteCalendar(Long calendarId) {
        try {
            log.info("Deleting events for calendar ID: {}", calendarId);
            eventsRepository.deleteByCalendarId(calendarId); // 이벤트 삭제

            log.info("Deleting calendar members for calendar ID: {}", calendarId);
            calendarMemberRepository.deleteByCalendarId(calendarId); // 멤버 삭제

            log.info("Deleting calendar with ID: {}", calendarId);
            calendarRepository.deleteById(calendarId); // 캘린더 삭제
        } catch (Exception e) {
            log.error("Error deleting calendar with ID: {}", calendarId, e);
            throw e;
        }
    }
}