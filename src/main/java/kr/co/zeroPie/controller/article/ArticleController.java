package kr.co.zeroPie.controller.article;

import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Map;

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

    // 게시판 글목록(card)
    @PostMapping("/article/card")
    public ResponseEntity<?> articleCard(@RequestBody ArticlePageRequestDTO articlePageRequestDTO) {

        ArticlePageResponseDTO articlePageResponseDTO = new ArticlePageResponseDTO();
        log.info("card 출력");

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
    public ResponseEntity<?> articleWrite(@RequestParam ArticleDTO articleDTO) {
        log.info("articleDTO : " + articleDTO);
        for (MultipartFile each : articleDTO.getFiles()) {
            log.info("files : " + each);
        }
        for (MultipartFile each : articleDTO.getImage()) {
            log.info("image : " + each);
        }
        log.info("글쓰기 완료");

        //지금 글쓰기 안됨(코드 새로 toast이미지 + 첨부파일에 맞게 새로 짜는중)
        //return articleService.articleWrite(articleDTO);
        return null;
    }

    @PostMapping("/article/uploadFiles")
    public ResponseEntity<?> uploadFiles(@RequestBody MultipartFile[] files) {
        for (MultipartFile each : files) {
            log.info("files : " + each);


        }

        log.info("파일만 쓰기");
        //return articleService.articleWrite(articleDTO);
        return null;
    }

    @PostMapping("/article/uploadImages")
    public ResponseEntity<?> uploadImages(@RequestBody MultipartFile[] image) {
        for (MultipartFile each : image) {
            log.info("files : " + each);


        }

        log.info("이미지만 쓰기");
        //return articleService.articleWrite(articleDTO);
        return null;
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