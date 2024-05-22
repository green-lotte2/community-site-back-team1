package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.service.admin.AdminArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminArticleController {

    private final AdminArticleService adminArticleService;

    @GetMapping("/admin/articleCateList")
    public String articleCateList() {
        adminArticleService.selectArticleCates();
        return "/admin/articleCateList";
    }

    @PostMapping("/admin/insertCate")
    public String insertArticleCate(ArticleCateDTO articleCateDTO) {

        ArticleCate articleCate = adminArticleService.insertArticleCate(articleCateDTO);
        log.info(articleCate);
        return "/admin/articleCateList";
    }

}
