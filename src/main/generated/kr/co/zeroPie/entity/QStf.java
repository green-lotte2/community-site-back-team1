package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStf is a Querydsl query type for Stf
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStf extends EntityPathBase<Stf> {

    private static final long serialVersionUID = -1843683489L;

    public static final QStf stf = new QStf("stf");

    public final NumberPath<Integer> dptNo = createNumber("dptNo", Integer.class);

    public final NumberPath<Integer> planStatusNo = createNumber("planStatusNo", Integer.class);

    public final NumberPath<Integer> rnkNo = createNumber("rnkNo", Integer.class);

    public final StringPath stfAddr1 = createString("stfAddr1");

    public final StringPath stfAddr2 = createString("stfAddr2");

    public final StringPath stfEmail = createString("stfEmail");

    public final DateTimePath<java.util.Date> stfEnt = createDateTime("stfEnt", java.util.Date.class);

    public final StringPath stfImg = createString("stfImg");

    public final StringPath stfName = createString("stfName");

    public final StringPath stfNo = createString("stfNo");

    public final StringPath stfPass = createString("stfPass");

    public final StringPath stfPh = createString("stfPh");

    public final DateTimePath<java.util.Date> stfQuit = createDateTime("stfQuit", java.util.Date.class);

    public final StringPath stfRole = createString("stfRole");

    public final NumberPath<Integer> stfZip = createNumber("stfZip", Integer.class);

    public QStf(String variable) {
        super(Stf.class, forVariable(variable));
    }

    public QStf(Path<? extends Stf> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStf(PathMetadata metadata) {
        super(Stf.class, metadata);
    }

}

