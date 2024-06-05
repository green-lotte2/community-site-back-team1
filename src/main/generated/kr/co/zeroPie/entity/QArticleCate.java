package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleCate is a Querydsl query type for ArticleCate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleCate extends EntityPathBase<ArticleCate> {

    private static final long serialVersionUID = 1458002079L;

    public static final QArticleCate articleCate = new QArticleCate("articleCate");

    public final StringPath articleCateCoRole = createString("articleCateCoRole");

    public final StringPath articleCateName = createString("articleCateName");

    public final NumberPath<Integer> articleCateNo = createNumber("articleCateNo", Integer.class);

    public final StringPath articleCateOutput = createString("articleCateOutput");

    public final NumberPath<Integer> articleCateStatus = createNumber("articleCateStatus", Integer.class);

    public final StringPath articleCateVRole = createString("articleCateVRole");

    public final StringPath articleCateWRole = createString("articleCateWRole");

    public QArticleCate(String variable) {
        super(ArticleCate.class, forVariable(variable));
    }

    public QArticleCate(Path<? extends ArticleCate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleCate(PathMetadata metadata) {
        super(ArticleCate.class, metadata);
    }

}

