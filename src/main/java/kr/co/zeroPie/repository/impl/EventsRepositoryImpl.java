package kr.co.zeroPie.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.Events;
import kr.co.zeroPie.entity.QEvents;
import kr.co.zeroPie.repository.custom.EventsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventsRepositoryImpl implements EventsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    //qdsl로 Calendar엔티티 열기
    private final QEvents qEvents = QEvents.events;

    @Override
    public List<Events> getEvents(String stfNo) {

        return jpaQueryFactory.selectFrom(qEvents)
                .where(qEvents.stfNo.eq(stfNo))
                .fetch();
    }
}