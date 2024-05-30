package kr.co.zeroPie.controller.article;

import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    // 게시판 게시판카테(articleCate) 출력
    @GetMapping("/article")
    public ResponseEntity<?> getArticleCate(@RequestParam("articleCateNo") int articleCateNo) {

        return articleService.selectArticleCate(articleCateNo);
    }

    // 게시판 글목록(list)
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

    // 게시판 글목록(card)
    @PostMapping("/article/card")
    public ResponseEntity<?> articleCard(@RequestBody ArticlePageRequestDTO articlePageRequestDTO) {

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

    // 게시판 글쓰기(폼)
    @GetMapping("/article/write")
    public ResponseEntity<?> articleWriteForm(@RequestParam("articleCateNo") String articleCateNo) {

        return ResponseEntity.status(HttpStatus.OK).body("글쓰기 폼으로 이동");
    }

    // 게시판 글쓰기(기능)
    @PostMapping("/article/write")
    public ResponseEntity<?> articleWrite(@RequestBody ArticleDTO articleDTO) {
        log.info("글쓰기 확인용 로그(들어오나?) : " + articleDTO);      // 로그 들어옴
        return articleService.articleWrite(articleDTO);
    }

    // 게시판 글보기
    @GetMapping("/article/view")
    public ResponseEntity<?> articleView(@RequestParam("articleNo") int articleNo) {

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
        log.info("수정 로그");
        log.info("수정할 게시글 번호는 : " + articleNo);

        return articleService.articleView(articleNo);
    }

    // 게시판 글수정(기능)
    @PostMapping("/article/modify")
    public ResponseEntity<?> articleModify(@RequestBody ArticleDTO articleDTO){
        log.info(String.valueOf(articleDTO.getArticleNo()));

        log.info("수정기능 로그 들어옴? : " + articleDTO);

        return articleService.articleModify(articleDTO);
    }

    // 게시판 글삭제
    @PostMapping("/article/delete")
    public ResponseEntity<?> articleDelete(@RequestBody Map<String, Integer> request) {
        log.info("삭제 로그");

        int articleNo = request.get("articleNo");
        log.info("삭제할 번호, 삭제할 곳의 카테고리 : " + request);

        return articleService.articleDelete(articleNo);
    }
}