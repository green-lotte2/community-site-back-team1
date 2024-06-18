package kr.co.zeroPie.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImageFile is a Querydsl query type for ImageFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImageFile extends EntityPathBase<ImageFile> {

    private static final long serialVersionUID = -46630095L;

    public static final QImageFile imageFile = new QImageFile("imageFile");

    public final NumberPath<Integer> articleNo = createNumber("articleNo", Integer.class);

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QImageFile(String variable) {
        super(ImageFile.class, forVariable(variable));
    }

    public QImageFile(Path<? extends ImageFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImageFile(PathMetadata metadata) {
        super(ImageFile.class, metadata);
    }

}

