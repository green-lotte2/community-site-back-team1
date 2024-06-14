package kr.co.zeroPie.entity.kanban;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 1518919989L;

    public static final QBoard board = new QBoard("board");

    public final NumberPath<Integer> boardIndex = createNumber("boardIndex", Integer.class);

    public final StringPath boardName = createString("boardName");

    public final StringPath card = createString("card");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> kanbanId = createNumber("kanbanId", Integer.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

