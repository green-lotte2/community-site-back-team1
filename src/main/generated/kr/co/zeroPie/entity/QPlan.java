package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlan is a Querydsl query type for Plan
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlan extends EntityPathBase<Plan> {

    private static final long serialVersionUID = -1319710417L;

    public static final QPlan plan = new QPlan("plan");

    public final NumberPath<Integer> planCost = createNumber("planCost", Integer.class);

    public final NumberPath<Integer> planDuration = createNumber("planDuration", Integer.class);

    public final StringPath planInfo = createString("planInfo");

    public final StringPath planName = createString("planName");

    public final NumberPath<Integer> planNo = createNumber("planNo", Integer.class);

    public QPlan(String variable) {
        super(Plan.class, forVariable(variable));
    }

    public QPlan(Path<? extends Plan> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlan(PathMetadata metadata) {
        super(Plan.class, metadata);
    }

}

