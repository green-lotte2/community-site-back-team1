package kr.co.zeroPie.service;

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
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;

    // 게시판 카테고리 표시
    public ResponseEntity<?> selectArticleCate(int articleCateNo){

        Optional<ArticleCate> articleCate = articleCateRepository.findById(articleCateNo);

        if (articleCate.isPresent()) {
            log.info("게시판 카테고리 조회 : " + articleCate);
            return ResponseEntity.status(HttpStatus.OK).body(articleCate.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article category not found");
        }
    }

    // 게시판 글목록 출력(일반)
    public ArticlePageResponseDTO selectArticles(ArticlePageRequestDTO articlePageRequestDTO) {

        Pageable pageable = articlePageRequestDTO.getPageable("articleNo");

        Page<Article> pageArticle = articleRepository.selectArticles(articlePageRequestDTO, pageable);

        List<Article> articleList = pageArticle.getContent();

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article each : articleList) {
            articleDTOList.add(modelMapper.map(each, ArticleDTO.class));
        }

        //log.info("selectArticles...4 : " + articleDTOList);

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

        Page<Article> pageArticle = articleRepository.searchArticles(articlePageRequestDTO, pageable);

        List<Article> articleList = pageArticle.getContent();

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article each : articleList) {
            articleDTOList.add(modelMapper.map(each, ArticleDTO.class));
        }

        int total = (int) pageArticle.getTotalElements();

        return ArticlePageResponseDTO.builder()
                .articlePageRequestDTO(articlePageRequestDTO)
                .dtoList(articleDTOList)
                .total(total)
                .build();
    }

    // 게시글 작성(write)
    public ResponseEntity<?> articleWrite(ArticleDTO articleDTO) {
        articleDTO.setArticleStatus("view");
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info("서비스 들어오냐? : " + article); // 여기까진 로그 찍힘


        /*
        // articleCnt에서 이미지 추출하여 articleThumb에 저장
        extractAndSaveArticleThumb(article);
*/

        Article savedArticle = articleRepository.save(article);

        log.info("레파지토리 갔다왔냐? : " + savedArticle); // 로그 찍힘
        if (savedArticle.getArticleCnt() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    /* 기존
    public ResponseEntity<?> articleWrite(ArticleDTO articleDTO) {
        articleDTO.setArticleStatus("view");
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info("서비스 들어오냐? : " + article);             // 여기까진 로그 찍힘

        Article savedArticle = articleRepository.save(article);

        log.info("레파지토리 갔다왔냐? : " + savedArticle);        // 로그인 찍힘
        if (savedArticle.getArticleCnt() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
     */


/*
    // articleCnt에서 이미지 추출하여 articleThumb에 저장하는 함수
    private void extractAndSaveArticleThumb(Article article) {
        String articleCnt = article.getArticleCnt();
        String articleThumb = extractFirstImage(articleCnt);
        article.setArticleThumb(articleThumb);
    }

    // articleCnt에서 첫 번째 이미지를 추출하는 함수
    private String extractFirstImage(String articleCnt) {
        String imageUrl = null;
        Pattern pattern = Pattern.compile("<img\\s+[^>]*?src\\s*=\\s*(['\"])(.*?)\\1");
        Matcher matcher = pattern.matcher(articleCnt);
        if (matcher.find()) {
            imageUrl = matcher.group(2);
        }
        return imageUrl;
    }

 */


    // 게시판 글보기(view)
    public ArticleDTO findById(int articleNo) {
        log.info("게시판 글 ");
        Optional<Article> optArticle = articleRepository.findById(articleNo);

        ArticleDTO articleDTO = null;

        if (optArticle.isPresent()) {
            articleDTO = modelMapper.map(optArticle.get(), ArticleDTO.class);
        }
        return articleDTO;
    }


    // 수정할 글보기(modifyForm)
    public ResponseEntity<?> articleView(int articleNo){

        Optional<?> optionalArticle = articleRepository.findById(articleNo);

        if (optionalArticle.isPresent()) {
            Article article = modelMapper.map(optionalArticle,Article.class);//게시글번호에 해당하는 게시글 데이터

            return ResponseEntity.status(HttpStatus.OK).body(article);
        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 조회수 증가
    public void updateHit(int articleNo) {
        Optional<Article> optArticle = articleRepository.findById(articleNo);

        if (optArticle.isPresent()) {
            optArticle.get().setArticleHit(optArticle.get().getArticleHit()+1);
            articleRepository.save(optArticle.get());
        }
    }

    // 게시글 수정(modify)
    public ResponseEntity<?> articleModify(ArticleDTO articleDTO) {
        log.info("서비스 들어옴? : " + articleDTO);
        try {
            Optional<Article> optionalArticle = articleRepository.findById(articleDTO.getArticleNo());

            if (!optionalArticle.isPresent()) {
                return ResponseEntity.status(404).body("Article not found");
            }

            Article oArticle = optionalArticle.get();
            ArticleDTO oArticleDTO = modelMapper.map(oArticle, ArticleDTO.class);

            oArticleDTO.setArticleTitle(articleDTO.getArticleTitle());
            oArticleDTO.setArticleCnt(articleDTO.getArticleCnt());

            Article article = modelMapper.map(oArticleDTO, Article.class);
            articleRepository.save(article);

            return ResponseEntity.status(HttpStatus.OK).body(1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while modifying the article");
        }
    }

    // 게시글 삭제(delete)
    public ResponseEntity<?> articleDelete(int articleNo) {
        articleRepository.deleteById(articleNo);
        Optional<Article> optArticle = articleRepository.findById(articleNo);

        if (optArticle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
}