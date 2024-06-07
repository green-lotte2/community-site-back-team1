package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCs is a Querydsl query type for Cs
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCs extends EntityPathBase<Cs> {

    private static final long serialVersionUID = -890758154L;

    public static final QCs cs = new QCs("cs");

    public final StringPath csCate = createString("csCate");

    public final StringPath csContent = createString("csContent");

    public final NumberPath<Integer> csHit = createNumber("csHit", Integer.class);

    public final NumberPath<Integer> csNo = createNumber("csNo", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> csRdate = createDateTime("csRdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> csReply = createNumber("csReply", Integer.class);

    public final StringPath csTitle = createString("csTitle");

    public final StringPath secret = createString("secret");

    public final StringPath stfName = createString("stfName");

    public final StringPath stfNo = createString("stfNo");

    public QCs(String variable) {
        super(Cs.class, forVariable(variable));
    }

    public QCs(Path<? extends Cs> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCs(PathMetadata metadata) {
        super(Cs.class, metadata);
    }

}

