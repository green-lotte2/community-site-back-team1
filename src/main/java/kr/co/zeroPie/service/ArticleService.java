package kr.co.zeroPie.service;

import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;

import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.repository.ArticleCateRepository;
import kr.co.zeroPie.repository.ArticleRepository;
import kr.co.zeroPie.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;

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

    public ResponseEntity<?> articleWrite(ArticleDTO articleDTO) {
        log.info("Start writing article");
        articleDTO.setArticleStatus("view");

        Article article = modelMapper.map(articleDTO, Article.class);
        Article savedArticle;
        try {
            savedArticle = articleRepository.save(article);
        } catch (Exception e) {
            log.error("Error saving article: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving article");
        }

        if (articleDTO.getFiles() != null && !articleDTO.getFiles().isEmpty()) {
            try {
                log.info("Uploading files for article ID: " + savedArticle.getArticleNo());
                uploadFiles(articleDTO.getFiles(), savedArticle.getArticleNo());
            } catch (IOException e) {
                log.error("File upload failed: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
            }
        }

        if (savedArticle.getArticleCnt() != null) {
            log.info("Article written successfully with ID: " + savedArticle.getArticleNo());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            log.warn("Article content is null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    private void uploadFiles(List<MultipartFile> files, int articleNo) throws IOException {
        Path uploadPath = Paths.get("uploads/orgArtImage");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String extension = getExtension(originalFilename);
            String fileName = UUID.randomUUID().toString() + "." + extension;
            log.info("Uploading file with encoded name: " + fileName);

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }





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