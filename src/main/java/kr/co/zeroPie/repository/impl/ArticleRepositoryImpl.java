package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.entity.*;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QArticle qArticle = QArticle.article;
    private final QArticleCate qArticleCate = QArticleCate.articleCate;
    private final QStf qStf = QStf.stf;

    @Override
    public Page<Article> selectArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable){

        log.info("impl1 : " + pageRequestDTO);
        log.info("impl1 : " + pageable);
        int cate = pageRequestDTO.getArticleCateNo();

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Article> results = jpaQueryFactory
                .select(qArticle)
                .from(qArticle)
                .where(qArticle.articleCateNo.eq(cate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.articleNo.desc())
                .fetchResults();

        //log.info("results : " + results.getResults());
        List<Article> content = results.getResults();
        //log.info("content : " + content);
        long total = results.getTotal();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Page<Article> searchArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable){

        int cate = pageRequestDTO.getArticleCateNo();

        // 타입 선택
        String type = pageRequestDTO.getType();
        log.info("type"+type);
        String keyword = pageRequestDTO.getKeyword();
        log.info("keyword"+keyword);
        // 날짜 선택
        LocalDate startDate = pageRequestDTO.getStartDate();

        LocalDate endDate = pageRequestDTO.getEndDate();
        String sort = pageRequestDTO.getSort();
        log.info("sort" + sort);
        // 정렬 선택
        //pageRequestDTO = new ArticlePageRequestDTO();

       // pageable = pageRequestDTO.getPageable(sort);

        // Querydsl 조건
        BooleanExpression expression = null;

        // 검색 종류에 따른 where 표현식
        switch (type) {
            case "title":
                expression = qArticle.articleTitle.contains(keyword);
                break;
            case "content":
                expression = qArticle.articleCnt.contains(keyword);
                break;
            case "title+content":
                BooleanExpression titleContains = qArticle.articleTitle.contains(keyword);
                BooleanExpression contentContains = qArticle.articleCnt.contains(keyword);
                expression = titleContains.or(contentContains);
                break;
            case "writer":
                expression = qArticle.writer.contains(keyword);
                break;
        }
        log.info("expression"+expression);

        BooleanExpression dateExpression = null;
        // 검색 기간에 따른 where 표현식
        if (startDate != null && endDate != null) {
            dateExpression = qArticle.articleRdate.between(startDate, endDate);
            if (dateExpression != null) {
                expression = (expression != null) ? expression.and(dateExpression) : dateExpression;
            }
        }


        // 검색 정렬에 따른 order 표현식
        OrderSpecifier<?> orderSpecifier = null;
        switch (sort) {
            case "default":
                // 기본 정렬: 게시물 번호 내림차순
                orderSpecifier = qArticle.articleNo.desc();

                break;
            case "hit":
                // 조회순 오름차순
                orderSpecifier = qArticle.articleHit.desc();
                break;
            case "latest":
                // 최신순: 게시물 등록일 내림차순
                orderSpecifier = qArticle.articleRdate.desc();
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sort);
        }
        log.info(expression.toString());

        QueryResults<Article> results = jpaQueryFactory
                .select(qArticle)
                .from(qArticle)
                .where(expression)
                .where(qArticle.articleCateNo.eq(cate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetchResults();

        List<Article> content = results.getResults();
        long total = results.getTotal();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }
}