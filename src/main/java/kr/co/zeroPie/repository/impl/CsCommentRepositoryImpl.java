package kr.co.zeroPie.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.*;
import kr.co.zeroPie.repository.custom.CsCommentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class CsCommentRepositoryImpl implements CsCommentRepositoryCustom {

    QStf qStf = QStf.stf;
    QCsComment qCsComment = QCsComment.csComment;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findByCsNo(int csNo) {

        List<Tuple> result = jpaQueryFactory
                .select(qCsComment,qStf.stfName,qStf.stfImg)
                .from(qCsComment)
                .join(qStf)
                .where(qCsComment.csNo.eq(csNo))
                .on(qCsComment.stfNo.eq(qStf.stfNo))
                .orderBy(qCsComment.csComRdate.desc())
                .fetch();

        log.info("임플에서 바로 뽑아보기 - "+result);

        return result;
    }
}
