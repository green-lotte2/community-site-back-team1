package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.CalendarMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarMemberRepository extends JpaRepository<CalendarMember, Long> {
    List<CalendarMember> findByStfNo(String stfNo);
    List<CalendarMember> findByCalendarId(Long calendarId);
    Optional<CalendarMember> findByCalendarIdAndStfNo(Long calendarId, String stfNo);

    void deleteByCalendarId(Long calendarId);
}
