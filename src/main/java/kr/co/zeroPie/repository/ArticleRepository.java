package kr.co.zeroPie.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
    int countByArticleCateNo(int articleCateNo);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.file = :fileCount where a.articleNo = :articleNo")
    int updateFileCount(@Param("fileCount") int fileCount, @Param("articleNo") int articleNo);

}