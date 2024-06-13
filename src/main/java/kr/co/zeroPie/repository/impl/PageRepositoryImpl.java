package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.QMyDoc;
import kr.co.zeroPie.entity.QPage;
import kr.co.zeroPie.repository.custom.PageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PageRepositoryImpl implements PageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMyDoc qMyDoc = QMyDoc.myDoc;
    private final QPage qPage = QPage.page;

    public List<Tuple> selectPageListById(String userId) {
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qMyDoc.pno, qPage.title, qPage.owner)
                .from(qMyDoc)
                .join(qPage)
                .on(qMyDoc.pno.eq(qPage.pno))
                .where(qMyDoc.stfNo.eq(userId))
                .fetchResults();

        List<Tuple> resultList = results.getResults();
        return resultList;
    }

}