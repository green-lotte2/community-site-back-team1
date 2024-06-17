package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRecords is a Querydsl query type for ChatRecords
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRecords extends EntityPathBase<ChatRecords> {

    private static final long serialVersionUID = -601409788L;

    public static final QChatRecords chatRecords = new QChatRecords("chatRecords");

    public final DateTimePath<java.time.LocalDateTime> dateTime = createDateTime("dateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath img = createString("img");

    public final StringPath message = createString("message");

    public final StringPath roomId = createString("roomId");

    public final StringPath stfName = createString("stfName");

    public final StringPath stfNo = createString("stfNo");

    public QChatRecords(String variable) {
        super(ChatRecords.class, forVariable(variable));
    }

    public QChatRecords(Path<? extends ChatRecords> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRecords(PathMetadata metadata) {
        super(ChatRecords.class, metadata);
    }

}

