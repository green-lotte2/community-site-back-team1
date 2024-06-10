package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.repository.custom.CalendarRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer>, CalendarRepositoryCustom {

    public List<Calendar> findByStfNo(String stfNo);
    public List<Calendar> findById(String id);
    public void deleteById(String id);
}
