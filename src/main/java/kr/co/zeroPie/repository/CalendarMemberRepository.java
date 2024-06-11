package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.CalendarMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarMemberRepository extends JpaRepository<CalendarMember, Long> {
}
