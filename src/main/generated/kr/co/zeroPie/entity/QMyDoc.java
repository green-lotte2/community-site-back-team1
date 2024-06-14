package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyDoc is a Querydsl query type for MyDoc
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyDoc extends EntityPathBase<MyDoc> {

    private static final long serialVersionUID = 2036239014L;

    public static final QMyDoc myDoc = new QMyDoc("myDoc");

    public final NumberPath<Integer> myDocNo = createNumber("myDocNo", Integer.class);

    public final NumberPath<Integer> pno = createNumber("pno", Integer.class);

    public final StringPath stfNo = createString("stfNo");

    public QMyDoc(String variable) {
        super(MyDoc.class, forVariable(variable));
    }

    public QMyDoc(Path<? extends MyDoc> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyDoc(PathMetadata metadata) {
        super(MyDoc.class, metadata);
    }

}

