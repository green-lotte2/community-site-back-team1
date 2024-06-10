package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = -1320010878L;

    public static final QFile file = new QFile("file");

    public final NumberPath<Integer> articleNo = createNumber("articleNo", Integer.class);

    public final NumberPath<Integer> fileDownload = createNumber("fileDownload", Integer.class);

    public final NumberPath<Integer> fileNo = createNumber("fileNo", Integer.class);

    public final StringPath fileOname = createString("fileOname");

    public final DateTimePath<java.time.LocalDateTime> fileRdate = createDateTime("fileRdate", java.time.LocalDateTime.class);

    public final StringPath fileSname = createString("fileSname");

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}

