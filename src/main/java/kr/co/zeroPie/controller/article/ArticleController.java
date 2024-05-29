package kr.co.zeroPie.controller.article;

import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;


    // 게시판(리스트형) 출력(post로 바꾸기)
    @PostMapping("/article/list")
    public ResponseEntity<?> articleList(@RequestBody ArticlePageRequestDTO articlePageRequestDTO) {

        ArticlePageResponseDTO articlePageResponseDTO = new ArticlePageResponseDTO();
        log.info("articlePageResponseDTO"+ articlePageRequestDTO.toString());

        if (articlePageRequestDTO.getType() == null) {

            // 일반 글 목록 조회
            articlePageResponseDTO = articleService.selectArticles(articlePageRequestDTO);
            log.info("일반글");
        } else {

            // 검색 글 목록 조회
            articlePageResponseDTO = articleService.searchArticles(articlePageRequestDTO);
            log.info("검색글");
        }
        //log.info("articlePageResponseDTO : " + articlePageResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(articlePageResponseDTO);
    }


    // 게시판 게시판카테(articleCate) 출력
    @GetMapping("/article")
    public ResponseEntity<?> getArticleCate(@RequestParam("articleCateNo") int articleCateNo) {

        return articleService.selectArticleCate(articleCateNo);
    }


    // 게시판(카드형) 출력(post로 바꾸기)
    @GetMapping("/article/card")
    public ResponseEntity<?> articleCard(@RequestParam("articleCateNo") String articleCateNo,
                                  ArticlePageRequestDTO articlePageRequestDTO) {

        ArticlePageResponseDTO articlePageResponseDTO;
        log.info("articlePageResponseDTO"+ articlePageRequestDTO.toString());
        log.info("articlePageRequestDTO"+articleCateNo);

        if (articlePageRequestDTO.getType() == null) {

            // 일반 글 목록 조회
            articlePageResponseDTO = articleService.selectArticles(articlePageRequestDTO);
            log.info("일반글");
        } else {

            // 검색 글 목록 조회
            articlePageResponseDTO = articleService.searchArticles(articlePageRequestDTO);
            log.info("검색글");
        }
        return ResponseEntity.status(HttpStatus.OK).body(articlePageResponseDTO);
    }

    // 글쓰기(폼)
    @GetMapping("/article/write")
    public ResponseEntity<?> articleWriteForm(@RequestParam("articleCateNo") String articleCateNo) {

        return ResponseEntity.status(HttpStatus.OK).body("글쓰기 폼으로 이동");
    }

    // 게시글 작성
    @PostMapping("/article/write")
    public ResponseEntity<?> articleWrite(@RequestBody ArticleDTO articleDTO) {
        log.info("글쓰기 확인용 로그(들어오나?) : " + articleDTO);      // 로그 들어옴
        return articleService.articleWrite(articleDTO);
    }

    // 게시판 글보기
    @GetMapping("/article/view")
    public ResponseEntity<?> articleView(@RequestParam("articleNo") int articleNo) {

        // 게시글 ID로 게시글 조회
        ArticleDTO articleDTO = articleService.findById(articleNo);

        // ResponseEntity로 응답 DTO 반환
        return ResponseEntity.status(HttpStatus.OK).body(articleDTO);
    }

    // 게시판 글수정(폼)
    @GetMapping("/article/modify")
    public ResponseEntity<?> articleModifyForm(@RequestParam("articleNo") int articleNo){
        log.info("modify 들어옴? : " + articleNo);

        return articleService.articleView(articleNo);
    }

    /*
    // 게시판 글수정(기능)
    @PostMapping("/article/modify")
    public ResponseEntity<?> articleModifyForm(@RequestParam("articleNo") int articleNo){
        log.info("modify 들어옴? : " + articleNo);

        return articleService.articleView(articleNo);
    }

     */
    // 게시글 삭제
    @PostMapping("/article/delete")
    public ResponseEntity<?> articleDelete(@RequestBody Map<String, Integer> request) {
        int articleNo = request.get("articleNo");
        log.info("articleNo : " + articleNo);
        return articleService.articleDelete(articleNo);
    }

}