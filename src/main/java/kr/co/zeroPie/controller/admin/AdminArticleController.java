package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.service.admin.AdminArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminArticleController {

    private final AdminArticleService adminArticleService;

    // 관리자 - 게시판 관리 - 게시판 목록
    @GetMapping("/admin/articleCateList")
    public ResponseEntity<?> articleCateList() {
        return adminArticleService.selectArticleCates();
    }

    // 관리자 - 게시판 관리 - 게시판 생성
    @PostMapping("/admin/insertCate")
    public ResponseEntity<?> insertArticleCate(ArticleCateDTO articleCateDTO) {

        return adminArticleService.insertArticleCate(articleCateDTO);
    }

    // 관리자 - 게시판 관리 - 게시판 삭제
    @DeleteMapping("/admin/deleteCate")
    public ResponseEntity<?> deleteArticleCate(@RequestParam("articleCateNo") int articleCateNo) {
        return adminArticleService.deleteArticleCate(articleCateNo);
    }

    // 관리자 - 게시판 관리 - 게시판 수정
    @PutMapping("/admin/modifyCate")
    public void modifyArticleCate(@RequestBody ArticleCateDTO articleCateDTO) {
        log.info("수정!!!!! : " + articleCateDTO);
        adminArticleService.modifyArticleCate(articleCateDTO);
    }


}
