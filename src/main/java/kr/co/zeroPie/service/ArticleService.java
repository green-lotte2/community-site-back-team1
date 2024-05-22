package kr.co.zeroPie.service;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;



    public ArticlePageResponseDTO selectArticles(ArticlePageRequestDTO articlePageRequestDTO){

        Pageable pageable = articlePageRequestDTO.getPageable("no");

        Page<Tuple> pageArticle = articleRepository.selectArticles(articlePageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple -> {
                    Article article = tuple.get(0, Article.class);
                    int articleNo = tuple.get(1, Integer.class);
                    String stfNo = tuple.get(2, String.class);
                    String articleTitle = tuple.get(3, String.class);
                    String articleCnt = tuple.get(4, String.class);
                    String articleRdate = tuple.get(5, String.class);
                    int articleHit = tuple.get(6, Integer.class);
                    int articleCateNo = tuple.get(7, Integer.class);
                    String articleThumb = tuple.get(8, String.class);

                    ArticleDTO articleDTO = modelMapper.map(article, ArticleDTO.class);
                    articleDTO.setArticleNo(articleNo);
                    articleDTO.setStfNo(stfNo);
                    articleDTO.setArticleTitle(articleTitle);
                    articleDTO.setArticleCnt(articleCnt);
                    articleDTO.setArticleRdate(articleRdate);
                    articleDTO.setArticleHit(articleHit);
                    articleDTO.setArticleCateNo(articleCateNo);
                    articleDTO.setArticleThumb(articleThumb);

                    return articleDTO;
                })
                .collect(Collectors.toList());

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .pageRequestDTO(articlePageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public ArticlePageResponseDTO searchArticles(ArticlePageRequestDTO articlePageRequestDTO){

        Pageable pageable = articlePageRequestDTO.getPageable("no");
        Page<Tuple> pageArticle = articleRepository.searchArticles(articlePageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            Article article = tuple.get(0, Article.class);
                            int articleNo = tuple.get(1, Integer.class);
                            String stfNo = tuple.get(2, String.class);
                            String articleTitle = tuple.get(3, String.class);
                            String articleCnt = tuple.get(4, String.class);
                            String articleRdate = tuple.get(5, String.class);
                            int articleHit = tuple.get(6, Integer.class);
                            int articleCateNo = tuple.get(7, Integer.class);
                            String articleThumb = tuple.get(8, String.class);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .pageRequestDTO(articlePageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
}