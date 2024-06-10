package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


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

    public final DatePath<java.time.LocalDate> articleRdate = createDate("articleRdate", java.time.LocalDate.class);

    public final StringPath articleStatus = createString("articleStatus");

    public final StringPath articleThumb = createString("articleThumb");

    public final StringPath articleTitle = createString("articleTitle");

    public final NumberPath<Integer> file = createNumber("file", Integer.class);

    public final ListPath<File, QFile> fileList = this.<File, QFile>createList("fileList", File.class, QFile.class, PathInits.DIRECT2);

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

