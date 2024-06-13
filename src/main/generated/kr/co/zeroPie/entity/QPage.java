package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPage is a Querydsl query type for Page
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPage extends EntityPathBase<Page> {

    private static final long serialVersionUID = -1319720811L;

    public static final QPage page = new QPage("page");

    public final StringPath document = createString("document");

    public final StringPath owner = createString("owner");

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> rDate = createDateTime("rDate", java.time.LocalDateTime.class);

    public final StringPath title = createString("title");

    public QPage(String variable) {
        super(Page.class, forVariable(variable));
    }

    public QPage(Path<? extends Page> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPage(PathMetadata metadata) {
        super(Page.class, metadata);
    }

}

