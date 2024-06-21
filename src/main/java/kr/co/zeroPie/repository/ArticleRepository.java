package kr.co.zeroPie.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
    int countByArticleCateNo(int articleCateNo);

    @Query("SELECT article.file FROM Article article WHERE article.articleNo = :articleNo")
    int selectFileByArticleNo(int articleNo);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.file = :fileCount where a.articleNo = :articleNo")
    int updateFileCount(@Param("fileCount") int fileCount, @Param("articleNo") int articleNo);


    // 최신순 5개만 조회
    public List<Article> findTop5ByArticleCateNoOrderByArticleRdateDesc(int articleCateNo);

}