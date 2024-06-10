package kr.co.zeroPie.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.Calendar;
import kr.co.zeroPie.entity.QCalendar;
import kr.co.zeroPie.repository.custom.CalendarRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CalendarRepositoryImpl implements CalendarRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    //qdsl로 Calendar엔티티 열기
    private final QCalendar qCalendar = QCalendar.calendar;

    @Override
    public List<Calendar> getCalendars(String stfNo) {

        return jpaQueryFactory.selectFrom(qCalendar)
                .where(qCalendar.stfNo.eq(stfNo))
                .fetch();
    }
}