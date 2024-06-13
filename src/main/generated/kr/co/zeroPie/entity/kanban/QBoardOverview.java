package kr.co.zeroPie.entity.kanban;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardOverview is a Querydsl query type for BoardOverview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardOverview extends EntityPathBase<BoardOverview> {

    private static final long serialVersionUID = 1303784910L;

    public static final QBoardOverview boardOverview = new QBoardOverview("boardOverview");

    public final StringPath boardName = createString("boardName");

    public final StringPath card = createString("card");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> kanbanId = createNumber("kanbanId", Integer.class);

    public final StringPath kanbanInfo = createString("kanbanInfo");

    public final StringPath kanbanName = createString("kanbanName");

    public final StringPath kanbanStf = createString("kanbanStf");

    public QBoardOverview(String variable) {
        super(BoardOverview.class, forVariable(variable));
    }

    public QBoardOverview(Path<? extends BoardOverview> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardOverview(PathMetadata metadata) {
        super(BoardOverview.class, metadata);
    }

}

