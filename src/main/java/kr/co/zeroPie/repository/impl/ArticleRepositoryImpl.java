package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.entity.QArticle;
import kr.co.zeroPie.entity.QArticleCate;
import kr.co.zeroPie.entity.QStf;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QArticle qArticle = QArticle.article;
    private QArticleCate qArticleCate = QArticleCate.articleCate;
    private QStf qStf = QStf.stf;

    @Override
    public Page<Tuple> selectArticles(ArticlePageRequestDTO articlePageRequestDTO, Pageable pageable) {
        // Article과 ArticleCate 테이블을 조인합니다.
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qArticle, qStf.stfName)
                .from(qArticle)
                .join(qArticleCate).on(qArticle.articleCateNo.eq(qArticleCate.articleCateNo)) // Article과 ArticleCate를 조인합니다.
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.articleNo.desc())
                .fetchResults();

        List<Tuple> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Tuple> searchArticles(ArticlePageRequestDTO articlePageRequestDTO, Pageable pageable) {
        String cate = articlePageRequestDTO.getCate();
        log.info("...!!!!!!"+cate);
        String type = articlePageRequestDTO.getType();
        String keyword = articlePageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        if (type.equals("title")) {
            expression = qArticleCate.articleCateName.eq(cate).and(qArticle.articleTitle.contains(keyword));
        } else if (type.equals("content")) {
            expression = qArticleCate.articleCateName.eq(cate).and(qArticle.articleCnt.contains(keyword));
        } else if (type.equals("title_content")) {
            BooleanExpression titleContains = qArticle.articleTitle.contains(keyword);
            BooleanExpression contentContains = qArticle.articleCnt.contains(keyword);
            expression = qArticleCate.articleCateName.eq(cate).and(titleContains.or(contentContains));
        } else if (type.equals("writer")) {
            expression = qArticleCate.articleCateName.eq(cate).and(qStf.stfName.contains(keyword));
            log.info("expression : " + expression);
        }

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qArticle, qStf.stfName)
                .from(qArticle)
                .join(qStf)
                .on(qArticle.writer.eq(qStf.stfNo))
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.articleNo.desc())
                .fetchResults();

        List<Tuple> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}