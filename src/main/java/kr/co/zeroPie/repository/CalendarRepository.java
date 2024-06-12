package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Calendar;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByOwnerStfNo(String ownerStfNo); // 개인 캘린더는 하나만 존재해야 함

    @Query("SELECT c FROM Calendar c JOIN CalendarMember cm ON c.calendarId = cm.calendarId WHERE cm.stfNo = :stfNo")
    List<Calendar> findAllSharedCalendars(@Param("stfNo") String stfNo); // 공유된 캘린더를 가져오는 쿼리
}
