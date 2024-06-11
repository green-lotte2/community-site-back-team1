package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCalendarMember is a Querydsl query type for CalendarMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarMember extends EntityPathBase<CalendarMember> {

    private static final long serialVersionUID = 1086292478L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCalendarMember calendarMember = new QCalendarMember("calendarMember");

    public final QCalendar calendar;

    public final NumberPath<Long> calMemId = createNumber("calMemId", Long.class);

    public final QStf stf;

    public QCalendarMember(String variable) {
        this(CalendarMember.class, forVariable(variable), INITS);
    }

    public QCalendarMember(Path<? extends CalendarMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCalendarMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCalendarMember(PathMetadata metadata, PathInits inits) {
        this(CalendarMember.class, metadata, inits);
    }

    public QCalendarMember(Class<? extends CalendarMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.calendar = inits.isInitialized("calendar") ? new QCalendar(forProperty("calendar"), inits.get("calendar")) : null;
        this.stf = inits.isInitialized("stf") ? new QStf(forProperty("stf")) : null;
    }

}

