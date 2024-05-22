package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.ArticlePageRequestDTO;
import kr.co.zeroPie.dto.ArticlePageResponseDTO;
import kr.co.zeroPie.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    // 리스트 출력
    @GetMapping("/article/list")
    public String list(Model model, ArticlePageRequestDTO articlePageRequestDTO) {

        ArticlePageResponseDTO articlePageResponseDTO = null;
        articlePageResponseDTO = articleService.selectArticles(articlePageRequestDTO);

          /*
        if(articlePageRequestDTO.getKeyword() == null) {
            // 일반 글 목록 조회
            log.info("조회(일반글) 결과 : " + articlePageResponseDTO);
        }
        else {
            // 검색 글 목록 조회
            articlePageResponseDTO = articleService.searchArticles(articlePageRequestDTO);
            log.info("조회(검색글) 결과 : " + articlePageResponseDTO);
        }
*/
        model.addAttribute(articlePageResponseDTO);

        return "/article/list";

    }
}