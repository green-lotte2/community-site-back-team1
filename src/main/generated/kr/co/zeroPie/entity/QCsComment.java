package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCsComment is a Querydsl query type for CsComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsComment extends EntityPathBase<CsComment> {

    private static final long serialVersionUID = -837604983L;

    public static final QCsComment csComment = new QCsComment("csComment");

    public final StringPath csComContent = createString("csComContent");

    public final NumberPath<Integer> csComNo = createNumber("csComNo", Integer.class);

    public final DatePath<java.time.LocalDate> csComRdate = createDate("csComRdate", java.time.LocalDate.class);

    public final NumberPath<Integer> csNo = createNumber("csNo", Integer.class);

    public final StringPath stfNo = createString("stfNo");

    public QCsComment(String variable) {
        super(CsComment.class, forVariable(variable));
    }

    public QCsComment(Path<? extends CsComment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsComment(PathMetadata metadata) {
        super(CsComment.class, metadata);
    }

}

