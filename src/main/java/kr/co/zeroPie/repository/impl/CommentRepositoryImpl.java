package kr.co.zeroPie.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.QComment;
import kr.co.zeroPie.entity.QCsComment;
import kr.co.zeroPie.entity.QStf;
import kr.co.zeroPie.repository.custom.CommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    QStf qStf = QStf.stf;
    QComment qComment = QComment.comment;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findByArticleNo(int articleNo) {

        List<Tuple> result = jpaQueryFactory
                .select(qComment,qStf.stfName,qStf.stfImg, qStf.stfNo)
                .from(qComment)
                .join(qStf)
                .where(qComment.articleNo.eq(articleNo))
                .on(qComment.stfNo.eq(qStf.stfNo))
                .orderBy(qComment.commentRdate.desc())
                .fetch();

        log.info("임플에서 바로 뽑아보기 - "+result);

        return result;
    }
}
