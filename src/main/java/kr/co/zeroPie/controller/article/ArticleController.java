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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.info("articlePageResponseDTO : " + articlePageResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(articlePageResponseDTO);
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
    public ResponseEntity<?> writeForm(@RequestParam("articleCateNo") String articleCateNo) {

        return ResponseEntity.status(HttpStatus.OK).body("글쓰기 폼으로 이동");
    }

    // 게시판 글보기
    @GetMapping("/article/view")
    public ResponseEntity<?> view(@RequestParam("articleNo") int articleNo) {

        // 게시글 ID로 게시글 조회
        ArticleDTO articleDTO = articleService.findById(articleNo);
        log.info(articleDTO.toString());
        // 조회된 데이터를 articlePageRequestDTO에 설정

        // ResponseEntity로 응답 DTO 반환
        return ResponseEntity.status(HttpStatus.OK).body(articleDTO);
    }

    // 게시판 글수정(폼)
    @GetMapping("/article/modify")
    public ResponseEntity<?> articleModifyFrom(@RequestParam("articleNo") int articleNo){

        return articleService.articleView(articleNo);
    }


/*
    // 글쓰기(기능)
    @PostMapping("/croptalk/write")
    public String croptalkWrite(@ModelAttribute ArticleDTO articleDTO, HttpServletRequest request) {

        articleDTO.setRegip(request.getRemoteAddr());
        articleService.insertArticle(articleDTO);

        log.info(articleDTO.toString());

        return "redirect:/croptalk/list?cate=" + articleDTO.getCate();
    }

    // 글수정(기능)
    @PostMapping("/croptalk/modify")
    public String croptalkModify(ArticleDTO articleDTO) {

        articleService.modifyArticle(articleDTO);

        return "redirect:/croptalk/view?no=" + articleDTO.getNo()+"&cate="+articleDTO.getCate();
    }

    // 글삭제(기능)
    @GetMapping("/croptalk/delete")
    public String croptalkDelete(int no, String cate) {
        fileService.deleteFiles(no);
        articleService.deleteArticle(no);

        return "redirect:/croptalk/list?cate=" + cate;
    }





     */
}