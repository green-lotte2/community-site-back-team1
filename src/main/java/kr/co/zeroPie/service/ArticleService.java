package kr.co.zeroPie.service;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.repository.ArticleCateRepository;
import kr.co.zeroPie.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;

    // 게시판 글목록 출력(일반)
    public ArticlePageResponseDTO selectArticles(ArticlePageRequestDTO articlePageRequestDTO) {

        log.info("selectArticles...1");
        Pageable pageable = articlePageRequestDTO.getPageable("articleNo");

        log.info("selectArticles-pageable : "+pageable);

        log.info("selectArticles...2");
        Page<Article> pageArticle = articleRepository.selectArticles(articlePageRequestDTO, pageable);

        log.info("selectArticles...3 : " + pageArticle.getContent());

        List<Article> articleList = pageArticle.getContent();

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article each : articleList) {
            articleDTOList.add(modelMapper.map(each, ArticleDTO.class));
        }

        log.info("selectArticles...4 : " + articleDTOList);

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .articlePageRequestDTO(articlePageRequestDTO)
                .dtoList(articleDTOList)
                .total(total)
                .build();
    }

    // 게시판 글목록 출력(검색)
    public ArticlePageResponseDTO searchArticles(ArticlePageRequestDTO articlePageRequestDTO) {

        Pageable pageable = articlePageRequestDTO.getPageable("articleNo");
        Page<Tuple> pageArticle = articleRepository.searchArticles(articlePageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            Article article = tuple.get(0, Article.class);
                            String writer = tuple.get(1, String.class);
                            article.setWriter(writer);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .articlePageRequestDTO(articlePageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 게시판 글보기
    public ArticleDTO findById(int articleNo) {

        Optional<Article> optArticle = articleRepository.findById(articleNo);

        ArticleDTO articleDTO = null;

        if (optArticle.isPresent()) {

            Article article = optArticle.get();
            articleDTO = modelMapper.map(article, ArticleDTO.class);
        }
        return articleDTO;
    }

    public ResponseEntity<?> articleView(int articleNo){

        Optional<?> optionalArticle = articleRepository.findById(articleNo);

        if (optionalArticle.isPresent()) {
            Article article = modelMapper.map(optionalArticle,Article.class);//게시글번호에 해당하는 게시글 데이터

            return ResponseEntity.status(HttpStatus.OK).body(article);
        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

/*
// 카테고리 검색(카테고리에 해당하는 Config 엔티티를 찾아 반환)
    public ArticleCate findArticleCateNo(int cate) {
        Integer cateId;

        try {
            cateId = cate;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid category id: " + cate, e);
        }
        return articleCateRepository.findById(cateId).orElseThrow(() -> new NoSuchElementException("Category not found"));
    }


    // 해당 카테고리의 게시글 목록을 가져옴
    public List<ArticleDTO> getArticlesByCate(int cate) {
        List<Article> articles = articleRepository.findByArticleCateNo(cate);

        // Entity를 DTO로 변환하여 반환한다.
        return articles.stream()
                .map(article -> {
                    ArticleDTO dto = new ArticleDTO();
                    dto.setArticleNo(article.getArticleNo());
                    dto.setStfNo(article.getStfNo());
                    dto.setArticleTitle(article.getArticleTitle());
                    dto.setArticleCnt(article.getArticleCnt());
                    dto.setArticleRdate(article.getArticleRdate());
                    dto.setArticleHit(article.getArticleHit());
                    dto.setArticleCateNo(article.getArticleCateNo());
                    dto.setArticleThumb(article.getArticleThumb());
                    dto.setWriter(article.getWriter());
                    return dto;
                })
                .collect(Collectors.toList());
    }




    public ArticlePageResponseDTO selectArticles(ArticlePageRequestDTO pageRequestDTO){

        log.info("selectArticles...1");
        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("selectArticles...2");
        Page<Tuple> pageArticle = articleRepository.selectArticles(pageRequestDTO, pageable);

        log.info("selectArticles...3 : " + pageArticle);
        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            Article article = tuple.get(0, Article.class);

                            String writer = tuple.get(1, String.class);
                            article.setWriter(writer);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .articlePageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public ArticlePageResponseDTO searchArticles(ArticlePageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");
        Page<Tuple> pageArticle = articleRepository.searchArticles(pageRequestDTO, pageable);

        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(tuple ->
                        {
                            Article article = tuple.get(0, Article.class);

                            String writer = tuple.get(1, String.class);
                            article.setWriter(writer);

                            return modelMapper.map(article, ArticleDTO.class);
                        }
                )
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .articlePageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }
 */
}