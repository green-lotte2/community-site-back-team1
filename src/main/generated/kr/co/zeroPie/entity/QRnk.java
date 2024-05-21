package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRnk is a Querydsl query type for Rnk
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRnk extends EntityPathBase<Rnk> {

    private static final long serialVersionUID = -1843684631L;

    public static final QRnk rnk = new QRnk("rnk");

    public final StringPath rnkName = createString("rnkName");

    public final NumberPath<Integer> rnkNo = createNumber("rnkNo", Integer.class);

    public QRnk(String variable) {
        super(Rnk.class, forVariable(variable));
    }

    public QRnk(Path<? extends Rnk> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRnk(PathMetadata metadata) {
        super(Rnk.class, metadata);
    }

}

