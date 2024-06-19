package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QToDo is a Querydsl query type for ToDo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QToDo extends EntityPathBase<ToDo> {

    private static final long serialVersionUID = -1319589268L;

    public static final QToDo toDo = new QToDo("toDo");

    public final StringPath stfNo = createString("stfNo");

    public final StringPath todoContent = createString("todoContent");

    public final DatePath<java.time.LocalDate> todoDate = createDate("todoDate", java.time.LocalDate.class);

    public final NumberPath<Integer> todoNo = createNumber("todoNo", Integer.class);

    public final StringPath todoStatus = createString("todoStatus");

    public QToDo(String variable) {
        super(ToDo.class, forVariable(variable));
    }

    public QToDo(Path<? extends ToDo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QToDo(PathMetadata metadata) {
        super(ToDo.class, metadata);
    }

}

