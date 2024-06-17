package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.service.ArticleService;
import kr.co.zeroPie.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final FileService fileService;

    // 게시판 카테고리 출력
    @GetMapping("/article")
    public ResponseEntity<?> getArticleCate(@RequestParam("articleCateNo") int articleCateNo) {
        log.info("현재 카테고리 : " + articleCateNo);
        return articleService.selectArticleCate(articleCateNo);
    }

    // 게시판 글목록(list)
    @PostMapping("/article/list")
    public ResponseEntity<?> articleList(@RequestBody ArticlePageRequestDTO articlePageRequestDTO) {

        ArticlePageResponseDTO articlePageResponseDTO = new ArticlePageResponseDTO();
        log.info("list 출력");

        if (articlePageRequestDTO.getType() == null) {
            // 일반 글 목록 조회
            articlePageResponseDTO = articleService.selectArticles(articlePageRequestDTO);
            log.info("일반글 조회 : " + articlePageResponseDTO);

        } else {
            // 검색 글 목록 조회
            articlePageResponseDTO = articleService.searchArticles(articlePageRequestDTO);
            log.info("검색글 조회 : " + articlePageResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(articlePageResponseDTO);
    }

    // 게시판 글쓰기 Form
    @GetMapping("/article/write")
    public ResponseEntity<?> articleWriteForm(@RequestParam("articleCateNo") String articleCateNo) {
        log.info("글쓰기");
        return ResponseEntity.status(HttpStatus.OK).body("글쓰기 폼으로 이동");
    }

    // 게시판 글쓰기 Function
    @PostMapping("/article/write")
    public ResponseEntity<?> articleWrite(@ModelAttribute ArticleDTO articleDTO) {
        return articleService.articleWrite(articleDTO);
    }

    @PostMapping("/article/uploadImage")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Uploading image: " + file.getOriginalFilename());
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID().toString() + "." + extension;

            Path uploadPath = Paths.get("uploads/orgArtImage");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Image uploaded successfully: " + fileName);
            return ResponseEntity.ok(Collections.singletonMap("name", fileName));
        } catch (IOException e) {
            log.error("Image upload failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Image upload failed"));
        }
    }

    // 게시판 글보기
    @GetMapping("/article/view")
    public ResponseEntity<?> articleView(@RequestParam("articleNo") int articleNo) {
        log.info("글보기");

        // 조회수 증가 로직 추가
        articleService.updateHit(articleNo);

        // 게시글 ID로 게시글 조회
        ArticleDTO articleDTO = articleService.findById(articleNo);

        // ResponseEntity로 응답 DTO 반환
        return ResponseEntity.status(HttpStatus.OK).body(articleDTO);
    }

    // 게시판 글수정(폼)
    @GetMapping("/article/modify")
    public ResponseEntity<?> articleModifyForm(@RequestParam("articleNo") int articleNo){
        log.info("글수정");

        return articleService.articleView(articleNo);
    }

    // 게시판 글수정(기능)
    @PostMapping("/article/modify")
    public ResponseEntity<?> articleModify(@RequestBody ArticleDTO articleDTO){
        log.info(String.valueOf(articleDTO.getArticleNo()));

        log.info("글수정 완료");

        return articleService.articleModify(articleDTO);
    }

    // 게시판 글삭제
    @PostMapping("/article/delete")
    public ResponseEntity<?> articleDelete(@RequestBody Map<String, Integer> request) {

        int articleNo = request.get("articleNo");
        //fileService.deleteFiles(articleNo);

        log.info("글 삭제 완료");

        return articleService.articleDelete(articleNo);
    }
}