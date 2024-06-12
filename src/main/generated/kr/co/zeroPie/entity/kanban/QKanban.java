package kr.co.zeroPie.entity.kanban;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKanban is a Querydsl query type for Kanban
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKanban extends EntityPathBase<Kanban> {

    private static final long serialVersionUID = 86984392L;

    public static final QKanban kanban = new QKanban("kanban");

    public final NumberPath<Integer> kanbanId = createNumber("kanbanId", Integer.class);

    public final StringPath kanbanInfo = createString("kanbanInfo");

    public final StringPath kanbanName = createString("kanbanName");

    public final StringPath kanbanStf = createString("kanbanStf");

    public QKanban(String variable) {
        super(Kanban.class, forVariable(variable));
    }

    public QKanban(Path<? extends Kanban> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKanban(PathMetadata metadata) {
        super(Kanban.class, metadata);
    }

}

