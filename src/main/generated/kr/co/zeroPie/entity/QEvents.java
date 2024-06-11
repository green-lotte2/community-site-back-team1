package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvents is a Querydsl query type for Events
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvents extends EntityPathBase<Events> {

    private static final long serialVersionUID = -1531920993L;

    public static final QEvents events = new QEvents("events");

    public final StringPath backgroundColor = createString("backgroundColor");

    public final NumberPath<Long> calendarId = createNumber("calendarId", Long.class);

    public final StringPath color = createString("color");

    public final DateTimePath<java.time.LocalDateTime> end = createDateTime("end", java.time.LocalDateTime.class);

    public final StringPath eventId = createString("eventId");

    public final NumberPath<Integer> eventNo = createNumber("eventNo", Integer.class);

    public final BooleanPath isAllDay = createBoolean("isAllDay");

    public final BooleanPath isReadOnly = createBoolean("isReadOnly");

    public final StringPath location = createString("location");

    public final DateTimePath<java.time.LocalDateTime> start = createDateTime("start", java.time.LocalDateTime.class);

    public final StringPath state = createString("state");

    public final StringPath stfNo = createString("stfNo");

    public final StringPath title = createString("title");

    public QEvents(String variable) {
        super(Events.class, forVariable(variable));
    }

    public QEvents(Path<? extends Events> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvents(PathMetadata metadata) {
        super(Events.class, metadata);
    }

}

