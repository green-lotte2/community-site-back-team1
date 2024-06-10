package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlanOrder is a Querydsl query type for PlanOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlanOrder extends EntityPathBase<PlanOrder> {

    private static final long serialVersionUID = -1375327393L;

    public static final QPlanOrder planOrder = new QPlanOrder("planOrder");

    public final NumberPath<Integer> cost = createNumber("cost", Integer.class);

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final StringPath paymentMethod = createString("paymentMethod");

    public final NumberPath<Integer> planStatusNo = createNumber("planStatusNo", Integer.class);

    public final StringPath stfEmail = createString("stfEmail");

    public final StringPath stfName = createString("stfName");

    public final StringPath stfPh = createString("stfPh");

    public QPlanOrder(String variable) {
        super(PlanOrder.class, forVariable(variable));
    }

    public QPlanOrder(Path<? extends PlanOrder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlanOrder(PathMetadata metadata) {
        super(PlanOrder.class, metadata);
    }

}

