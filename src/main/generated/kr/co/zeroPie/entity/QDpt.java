package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDpt is a Querydsl query type for Dpt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDpt extends EntityPathBase<Dpt> {

    private static final long serialVersionUID = -1843698014L;

    public static final QDpt dpt = new QDpt("dpt");

    public final StringPath dptName = createString("dptName");

    public final NumberPath<Integer> dptNo = createNumber("dptNo", Integer.class);

    public QDpt(String variable) {
        super(Dpt.class, forVariable(variable));
    }

    public QDpt(Path<? extends Dpt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDpt(PathMetadata metadata) {
        super(Dpt.class, metadata);
    }

}

