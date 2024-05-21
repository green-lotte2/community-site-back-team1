package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 2081989273L;

    public static final QComment comment = new QComment("comment");

    public final NumberPath<Integer> articleNo = createNumber("articleNo", Integer.class);

    public final StringPath commentCnt = createString("commentCnt");

    public final NumberPath<Integer> commentNo = createNumber("commentNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> commentRdate = createDateTime("commentRdate", java.time.LocalDateTime.class);

    public final StringPath stfNo = createString("stfNo");

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

