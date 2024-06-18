package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Calendar;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByOwnerStfNo(String ownerStfNo);

    @Query("SELECT c FROM Calendar c JOIN CalendarMember cm ON c.calendarId = cm.calendarId WHERE cm.stfNo = :stfNo")
    List<Calendar> findAllSharedCalendars(@Param("stfNo") String stfNo);

    void deleteById(Long calendarId);
}
