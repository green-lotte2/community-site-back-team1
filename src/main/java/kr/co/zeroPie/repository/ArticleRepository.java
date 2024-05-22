package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
}
