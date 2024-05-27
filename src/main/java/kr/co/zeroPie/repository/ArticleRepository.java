package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {

    //public Page<Article> findByArticleCate(String cate, Pageable pageable);

    List<Article> findByArticleCateNo(int cate);
}
