package kr.co.zeroPie.controller.page;

import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.PageDTO;
import kr.co.zeroPie.dto.kanban.KanbanStfDTO;
import kr.co.zeroPie.service.page.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    // 새 페이지 만들기
    @PostMapping("/doc/create")
    public ResponseEntity<?> docCreate(@RequestBody String userId) {

        log.info("userId : " + userId);

        return pageService.createNewDoc(userId);
    }

    // 문서 파일 저장
    @PostMapping("/doc/file")
    public ResponseEntity<?> docFile(@RequestParam("file") MultipartFile file, @RequestParam("pno") int pno) {

        log.info("file : " + file);
        log.info("pno : " + pno);

        return pageService.insertDocFile(file, pno);
    }

    // 현재 문서의 공동 작업자 목록 조회
    @GetMapping("/doc/member/{pno}")
    public ResponseEntity<?> docMember(@PathVariable("pno") int pno) {

        log.info("pno : " + pno);
        return pageService.selectDocMember(pno);
    }

    // 현재 문서의 공동 작업자 초대
    @PostMapping("/doc/addMember")
    public ResponseEntity<?> addDocMember(@RequestBody Map<String, Object> requestData) {
        log.info("requestData : " + requestData);
        pageService.insertDocMember(requestData);
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // 현재 문서 삭제
    @GetMapping("/doc/delete/{pno}")
    public ResponseEntity<?> docDelete(@PathVariable("pno") int pno) {

        log.info("pno : " + pno);
        return pageService.deleteDoc(pno);
    }

    // 현재 문서 나가기
    @PostMapping("/doc/exit")
    public ResponseEntity<?> docExit(@RequestBody Map<String, Object> requestData) {
        return pageService.exitDoc(requestData);
    }
}
