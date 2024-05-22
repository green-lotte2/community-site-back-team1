package kr.co.zeroPie.controller.article;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;


    // 게시판 목록 출력
    @GetMapping("/article/list")
    public ResponseEntity<?> list(@RequestParam("articleCateNo") String articleCateNo,
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


    @GetMapping("/article/card")
    public String card(@RequestParam("cate") int cate, Model model,
                       ArticlePageRequestDTO articlePageRequestDTO, @RequestParam(value = "pg", required = false) Integer pg) {

        ArticlePageResponseDTO articlePageResponseDTO;

        if (articlePageRequestDTO.getType() == null) {
            if (pg != null) {
                articlePageRequestDTO.setPg(pg);
            }

            // 일반 글 목록 조회
            articlePageResponseDTO = articleService.selectArticles(articlePageRequestDTO);

        } else {
            if (pg != null) {
                articlePageRequestDTO.setPg(pg);
            }

            // 검색 글 목록 조회
            articlePageResponseDTO = articleService.searchArticles(articlePageRequestDTO);

            model.addAttribute("keyword", articlePageRequestDTO.getKeyword());
            model.addAttribute("type", articlePageRequestDTO.getType());
        }
        model.addAttribute(articlePageResponseDTO);

        List<ArticleDTO> articles = articleService.getArticlesByCate(cate);
        model.addAttribute("articles", articles);

        ArticleCate articleCateName = articleService.findArticleCateNo(cate);
        model.addAttribute("articleCateName", articleCateName);

        return "/article/card";
    }

    // 게시판 글보기
    @GetMapping("/article/view")
    public String croptalkView(@RequestParam("cate") int cate, Model model, @RequestParam("no") int no) {

        ArticleDTO articleDTO = articleService.findById(no);
        ArticleCate cateName = articleService.findArticleCateNo(cate);

        if (articleDTO != null) {
            model.addAttribute("articleDTO", articleDTO);
        }

        model.addAttribute("cateName", cateName);
        //model.addAttribute("cate", articleDTO.getCate());

        return "/article/view";
    }


    // 글쓰기(폼)
    @GetMapping("/article/write")
    public ResponseEntity<?> articleWriteForm(@RequestParam("articleCateNo") int articleCateNo, Model model, HttpSession session) {

        String stfName = (String) session.getAttribute("stfName");
        model.addAttribute("stfName", stfName);

        ArticleCate cateName = articleService.findArticleCateNo(articleCateNo);
        model.addAttribute("cateName", cateName);

        return  ResponseEntity.status(HttpStatus.OK).body(cateName);
    }

/*

    @GetMapping("/article/list")
    public String list(Model model, String cate, ArticlePageRequestDTO pageRequestDTO){

        ArticlePageResponseDTO pageResponseDTO = null;

        if(pageRequestDTO.getKeyword() == null) {
            // 일반 글 목록 조회
            pageResponseDTO = articleService.selectArticles(pageRequestDTO);
            log.info("일반글 목록 조회 : " + pageResponseDTO);
        }else {
            // 검색 글 목록 조회
            pageResponseDTO = articleService.searchArticles(pageRequestDTO);
            log.info("검색글 목록 조회 : " + pageResponseDTO);
        }



        model.addAttribute(pageResponseDTO);

        return "/article/list";
    }

     */
}