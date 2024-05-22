package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 399212848L;

    public static final QArticle article = new QArticle("article");

    public final NumberPath<Integer> articleCateNo = createNumber("articleCateNo", Integer.class);

    public final StringPath articleCnt = createString("articleCnt");

    public final NumberPath<Integer> articleHit = createNumber("articleHit", Integer.class);

    public final NumberPath<Integer> articleNo = createNumber("articleNo", Integer.class);

    public final StringPath articleRdate = createString("articleRdate");

    public final StringPath articleThumb = createString("articleThumb");

    public final StringPath articleTitle = createString("articleTitle");

    public final StringPath stfNo = createString("stfNo");

    public final StringPath writer = createString("writer");

    public QArticle(String variable) {
        super(Article.class, forVariable(variable));
    }

    public QArticle(Path<? extends Article> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticle(PathMetadata metadata) {
        super(Article.class, metadata);
    }

}

