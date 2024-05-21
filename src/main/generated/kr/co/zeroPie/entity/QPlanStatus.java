package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlanStatus is a Querydsl query type for PlanStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlanStatus extends EntityPathBase<PlanStatus> {

    private static final long serialVersionUID = 430812673L;

    public static final QPlanStatus planStatus = new QPlanStatus("planStatus");

    public final DateTimePath<java.util.Date> planEdate = createDateTime("planEdate", java.util.Date.class);

    public final NumberPath<Integer> planNo = createNumber("planNo", Integer.class);

    public final DateTimePath<java.util.Date> planSdate = createDateTime("planSdate", java.util.Date.class);

    public final NumberPath<Integer> planStatusNo = createNumber("planStatusNo", Integer.class);

    public QPlanStatus(String variable) {
        super(PlanStatus.class, forVariable(variable));
    }

    public QPlanStatus(Path<? extends PlanStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlanStatus(PathMetadata metadata) {
        super(PlanStatus.class, metadata);
    }

}

