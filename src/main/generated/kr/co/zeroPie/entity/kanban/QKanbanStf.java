package kr.co.zeroPie.entity.kanban;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKanbanStf is a Querydsl query type for KanbanStf
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKanbanStf extends EntityPathBase<KanbanStf> {

    private static final long serialVersionUID = 1486826045L;

    public static final QKanbanStf kanbanStf = new QKanbanStf("kanbanStf");

    public final NumberPath<Integer> kanbanId = createNumber("kanbanId", Integer.class);

    public final NumberPath<Integer> kanbanStfNo = createNumber("kanbanStfNo", Integer.class);

    public final StringPath stfNo = createString("stfNo");

    public QKanbanStf(String variable) {
        super(KanbanStf.class, forVariable(variable));
    }

    public QKanbanStf(Path<? extends KanbanStf> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKanbanStf(PathMetadata metadata) {
        super(KanbanStf.class, metadata);
    }

}

