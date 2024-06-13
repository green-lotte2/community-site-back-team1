package kr.co.zeroPie.controller.page;

import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.PageDTO;
import kr.co.zeroPie.service.page.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PageController {

    private final PageService pageService;

    // 내 문서 목록 불러오기
    @PostMapping("/doc/list")
    public ResponseEntity<?> docList(@RequestBody String userId) {

        log.info("userId : " + userId);

        return pageService.selectPageList(userId);
    }

    // 페이지 데이터 저장
    @PostMapping("/doc/save")
    public ResponseEntity<?> docSave(@RequestBody PageDTO pageDTO) {

        log.info("pageDTO : " + pageDTO);

        pageService.insertPageContent(pageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // 페이지 데이터 불러오기
    @PostMapping("/doc/view")
    public ResponseEntity<?> docView(@RequestBody int pno) {

        log.info("pno : " + pno);

        return pageService.selectPageContent(pno);
    }
}
