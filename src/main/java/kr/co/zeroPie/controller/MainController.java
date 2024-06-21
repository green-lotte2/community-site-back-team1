package kr.co.zeroPie.controller;

import kr.co.zeroPie.dto.CsDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.dto.ToDoDTO;
import kr.co.zeroPie.repository.CsRepository;
import kr.co.zeroPie.service.ArticleService;
import kr.co.zeroPie.service.CsService;
import kr.co.zeroPie.service.MainService;
import kr.co.zeroPie.service.StfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final StfService stfService;
    private final ArticleService articleService;
    private final MainService mainService;

    // 메인 페이지 정보 조회
    @PostMapping("/main")
    public ResponseEntity<?> mainInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        // 한번에 반환시킬 Map
        Map<String, List<?>> resultMap = new HashMap<>();

        // 공지사항 조회
        resultMap.put("noticeDTO", articleService.selectNoticeForMain());

        // 개인 정보 조회
        resultMap.put("stfDTO", stfService.selectStfInfo(userId));

        // 생일 정보 조회
        resultMap.put("birthDTO", stfService.selectStfForBrith());

        // todo 조회
        resultMap.put("todoDTO", stfService.selectStfTodo(userId));
        
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    // 마이페이지 회원 정보 조회
    @PostMapping("/myPage")
    public ResponseEntity<?> myPageInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        List<StfDTO> stfDTOList = stfService.selectStfInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(stfDTOList.get(0));
    }

    // 마이페이지 연락처 수정 정보 저장
    @GetMapping("/myPage/stfPhSave")
    public ResponseEntity<?> stfPhSave(@RequestParam("stfPh") String stfPh, @RequestParam("stfNo") String stfNo) {
        return mainService.stfPhSave(stfPh, stfNo);
    }

    // 마이페이지 이메일 수정 정보 저장
    @GetMapping("/myPage/stfEmailSave")
    public ResponseEntity<?> stfEmailSave(@RequestParam("stfEmail") String stfEmail, @RequestParam("stfNo") String stfNo) {
        return mainService.stfEmailSave(stfEmail, stfNo);
    }

    // 마이페이지 주소 수정 정보 저장
    @PostMapping("/myPage/stfAddrSave")
    public ResponseEntity<?> stfAddrSave(@RequestBody Map<String, Object> requestMap) {
        return mainService.stfAddrSave(requestMap);
    }

    // 마이페이지 프로필 수정 정보 저장
    @PostMapping("/myPage/stfProfileSave")
    public ResponseEntity<?> stfProfileSave(@RequestParam("file") MultipartFile file,
                                            @RequestParam("stfNo") String stfNo) {
        return mainService.stfProfileSave(file, stfNo);
    }

    // 메인페이지 todo 완료
    @GetMapping("/todo/complete")
    public ResponseEntity<?> todoComplete(@RequestParam("todoNo") int todoNo) {
        log.info("todoNo : " + todoNo);
        return mainService.todoComplete(todoNo);
    }

    // 메인페이지 todo 생성
    @PostMapping("/todo/create")
    public ResponseEntity<?> createTodo(@RequestBody ToDoDTO toDoDTO) {
        log.info("toDoDTO : " + toDoDTO);
        return mainService.createTodo(toDoDTO);
    }
}
