package kr.co.zeroPie.service;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.*;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.ImageFile;
import kr.co.zeroPie.repository.ArticleCateRepository;
import kr.co.zeroPie.repository.ArticleRepository;
import kr.co.zeroPie.repository.CommentRepository;
import kr.co.zeroPie.repository.ImageFileRepository;
import kr.co.zeroPie.util.FilePathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;
    private final ImageFileRepository imageFileRepository;

    // 게시판 카테고리 표시
    public ResponseEntity<?> selectArticleCate(int articleCateNo) {
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
        List<ArticleDTO> articleDTOList = articleList.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
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
        List<ArticleDTO> articleDTOList = articleList.stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
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
        if (savedArticle.getArticleCnt() != null) {
            int articleNo = savedArticle.getArticleNo();
            log.info("Article written successfully with ID: " + savedArticle.getArticleNo());
            saveImagePaths(savedArticle.getArticleNo(), articleDTO.getArticleCnt());
            return ResponseEntity.status(HttpStatus.OK).body(articleNo);
        } else {
            log.warn("Article content is null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 이미지 경로 저장 로직
    private void saveImagePaths(int articleNo, String articleContent) {
        List<String> imagePaths = extractImagePaths(articleContent);
        imagePaths.forEach(imagePath -> {
            ImageFileDTO imageFileDTO = ImageFileDTO.builder()
                    .articleNo(articleNo)
                    .filePath(imagePath)
                    .build();
            ImageFile imageFile = imageFileDTO.toEntity();
            imageFileRepository.save(imageFile);
        });
    }

    // 이미지 경로 추출 로직
    private List<String> extractImagePaths(String content) {
        Pattern pattern = Pattern.compile("src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(content);
        return matcher.results()
                .map(match -> match.group(1))
                .collect(Collectors.toList());
    }

    // 게시판 글보기(view)
    public ArticleDTO findById(int articleNo) {
        log.info("게시판 글 ");
        Optional<Article> optArticle = articleRepository.findById(articleNo);
        log.info(optArticle.toString());
        return optArticle.map(article -> modelMapper.map(article, ArticleDTO.class)).orElse(null);
    }

    // 수정할 글보기(modifyForm)
    public ResponseEntity<?> articleView(int articleNo) {
        Optional<Article> optionalArticle = articleRepository.findById(articleNo);
        if (optionalArticle.isPresent()) {
            Article article = modelMapper.map(optionalArticle.get(), Article.class);
            return ResponseEntity.status(HttpStatus.OK).body(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 조회수 증가
    public void updateHit(int articleNo) {
        Optional<Article> optArticle = articleRepository.findById(articleNo);
        optArticle.ifPresent(article -> {
            article.setArticleHit(article.getArticleHit() + 1);
            articleRepository.save(article);
        });
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

            // 기존 이미지 파일 경로 추출
            List<String> existingImagePaths = extractImagePaths(oArticle.getArticleCnt());

            oArticle.setArticleTitle(articleDTO.getArticleTitle());
            oArticle.setArticleCnt(articleDTO.getArticleCnt());
            if (articleDTO.getArticleThumb() != null) {
                oArticle.setArticleThumb(articleDTO.getArticleThumb());
            }

            articleRepository.save(oArticle);

            // 새로운 이미지 파일 경로 추출
            List<String> newImagePaths = extractImagePaths(articleDTO.getArticleCnt());

            // 사용되지 않는 기존 이미지 파일 삭제
            List<String> unusedImagePaths = existingImagePaths.stream()
                    .filter(path -> !newImagePaths.contains(path))
                    .collect(Collectors.toList());
            deleteUnusedImages(unusedImagePaths);

            // 새로운 이미지 파일 경로 저장
            saveImagePaths(articleDTO.getArticleNo(), articleDTO.getArticleCnt());

            return ResponseEntity.status(HttpStatus.OK).body(1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while modifying the article");
        }
    }

    // 사용되지 않는 이미지 파일 삭제 로직
    private void deleteUnusedImages(List<String> unusedImagePaths) {
        for (String imagePath : unusedImagePaths) {
            try {
                String filePath = FilePathUtil.extractFilePathFromURL(imagePath);
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            } catch (Exception e) {
                log.error("Error deleting unused image file: " + imagePath, e);
            }
        }
    }

    // 게시글 삭제(delete)
    @Transactional
    public ResponseEntity<?> articleDelete(int articleNo) {
        // 관련된 파일 삭제
        fileService.fileDeleteWithArticle(articleNo);
        // 관련된 댓글 삭제
        commentRepository.deleteByArticleNo(articleNo);

        // 이미지 파일 삭제
        List<ImageFile> imageFiles = imageFileRepository.findByArticleNo(articleNo);
        deleteImageFiles(imageFiles);
        // 이미지 파일 DB 레코드 삭제
        imageFileRepository.deleteByArticleNo(articleNo);

        // 게시글 삭제
        articleRepository.deleteById(articleNo);

        Optional<Article> optArticle = articleRepository.findById(articleNo);
        if (optArticle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 이미지 파일 삭제 로직
    private void deleteImageFiles(List<ImageFile> imageFiles) {
        for (ImageFile imageFile : imageFiles) {
            try {
                String filePath = FilePathUtil.extractFilePathFromURL(imageFile.getFilePath());
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            } catch (Exception e) {
                log.error("Error deleting image file: " + imageFile.getFilePath(), e);
            }
        }
    }

    // 메인 페이지 공지사항 출력용
    public List<ArticleDTO> selectNoticeForMain() {
        List<Article> noticeList = articleRepository.findTop5ByArticleCateNoOrderByArticleRdateDesc(1);
        return noticeList.stream().map(Article -> modelMapper.map(Article, ArticleDTO.class)).toList();
    }

}
