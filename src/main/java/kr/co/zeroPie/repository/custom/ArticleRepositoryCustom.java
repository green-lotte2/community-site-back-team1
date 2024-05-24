package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {

    public Page<Article> selectArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable);

    public Page<Article> searchArticles(ArticlePageRequestDTO pageRequestDTO, Pageable pageable);

}
