package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalendarMember is a Querydsl query type for CalendarMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCalendarMember extends EntityPathBase<CalendarMember> {

    private static final long serialVersionUID = 1086292478L;

    public static final QCalendarMember calendarMember = new QCalendarMember("calendarMember");

    public final NumberPath<Long> calendarId = createNumber("calendarId", Long.class);

    public final NumberPath<Long> calMemId = createNumber("calMemId", Long.class);

    public final StringPath stfNo = createString("stfNo");

    public QCalendarMember(String variable) {
        super(CalendarMember.class, forVariable(variable));
    }

    public QCalendarMember(Path<? extends CalendarMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalendarMember(PathMetadata metadata) {
        super(CalendarMember.class, metadata);
    }

}

