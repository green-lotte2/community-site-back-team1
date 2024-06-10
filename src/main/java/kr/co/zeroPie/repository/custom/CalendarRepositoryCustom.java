package kr.co.zeroPie.repository.custom;

import kr.co.zeroPie.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepositoryCustom {
    public List<Calendar> getCalendars(String uid);
}
