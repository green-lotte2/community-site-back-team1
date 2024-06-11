package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Events;
import kr.co.zeroPie.repository.custom.EventsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer>, EventsRepositoryCustom {
    List<Events> findByStfNoAndCalendarId(String stfNo, Long calendarId);
    List<Events> findByEventId(String eventId);
    void deleteByEventId(String eventId);
}
