package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
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

        log.info("results : " + results.getResults());
        List<Article> content = results.getResults();
        log.info("content : " + content);
        long total = results.getTotal();

        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Page<Tuple> searchArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable){

        int cate = pageRequestDTO.getArticleCateNo();
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        // 검색 종류에 따른 where 표현식 생성
        if(type.equals("title")) {
            expression = qArticle.articleTitle.contains(keyword);

        }else if(type.equals("content")) {
            expression = qArticle.articleCnt.contains(keyword);

        }else if(type.equals("title_content")) {
            BooleanExpression titleContains = qArticle.articleTitle.contains(keyword);
            BooleanExpression contentContains = qArticle.articleCnt.contains(keyword);
            expression = titleContains.or(contentContains);

        }else if(type.equals("writer")) {
            expression = qStf.stfName.contains(keyword);
        }

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qArticle, qStf.stfName)
                .from(qArticle)
                .join(qStf)
                .on(qArticle.writer.eq(qStf.stfName))
                .where(expression)
                .where(qArticle.articleCateNo.eq(cate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.articleNo.desc())
                .fetchResults();


        List<Tuple> content = results.getResults();
        long total = results.getTotal();


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }

/*
    @Override
    public Page<Tuple> selectArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable){

        String cate = pageRequestDTO.getCate();

        log.info("selectArticles...1-1 : " + cate);

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qArticle, qStf.stfName)
                .from(qArticle)
                //.where(qArticle.cate.eq(cate).and(qArticle.parent.eq(0)))
                .join(qStf)
                .on(qArticle.writer.eq(qStf.stfNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qArticle.articleNo.desc())
                .fetchResults();


        log.info("selectArticles...1-2 : " + cate);

        List<Tuple> content = results.getResults();

        log.info("selectArticles...1-3 : " + cate);

        long total = results.getTotal();


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Page<Tuple> searchArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable){

        String cate = pageRequestDTO.getCate();
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanExpression expression = null;

        // 검색 종류에 따른 where 표현식 생성
        if(type.equals("title")) {
            expression = qArticle.cate.eq(cate).and(qArticle.articleTitle.contains(keyword));
            log.info("expression : " + expression);
        }else if(type.equals("content")) {
            expression = qArticle.cate.eq(cate).and(qArticle.articleCnt.contains(keyword));
            log.info("expression : " + expression);
        }else if(type.equals("title_content")) {

            BooleanExpression titleContains = qArticle.articleTitle.contains(keyword);
            BooleanExpression contentContains = qArticle.articleCnt.contains(keyword);
            expression = qArticle.cate.eq(cate).and(titleContains.or(contentContains));
            log.info("expression : " + expression);

        }else if(type.equals("writer")) {

            expression = qArticle.cate.eq(cate).and(qArticle.parent.eq(0)).and(qUser.nick.contains(keyword));
            log.info("expression : " + expression);

        }

        // 부가적인 Query 실행 정보를 처리하기 위해 fetchResults()로 실행
        // select * from article where `cate` ='notice' and `type` contains(k) limit 0,10
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


        // 페이징 처리를 위해 page 객체 리턴
        return new PageImpl<>(content, pageable, total);
    }
        */
}