package kr.co.zeroPie.controller;


import kr.co.zeroPie.dto.*;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.CsComment;
import kr.co.zeroPie.service.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CsController {

    private final CsService csService;


    //고객센터 리스트 전체 출력
    @PostMapping("/cs/list")
    public ResponseEntity<?> csList(@RequestBody  PageRequestDTO pageRequestDTO){//문의하기 리스트 띄우기
        log.info("문의하기 리스트 띄웁니다.");
        log.info("받아온고니 ?  : pageRequestDTO: {}", pageRequestDTO);

        ResponseEntity<?> csList= csService.list(pageRequestDTO);

        log.info("여기는 컨트롤러에 있는 csList: {}", csList.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(csList.getBody());
    }

    
    //등록
    @PostMapping("/cs/register")
    public ResponseEntity<?> csRegister(@RequestBody CsDTO csDTO){

        log.info("문의하기 등록입니다.");
        log.info("csDTO: {}", csDTO);

        csService.csRegister(csDTO);

        return ResponseEntity.status(HttpStatus.OK).body(csDTO);//상태.데이터(왼쪽 순으로)
    }

    //보기
    @GetMapping("/cs/view")
    public ResponseEntity<?> csView(@RequestParam("csNo") int csNo){

        log.info("csNo : "+csNo);

        return csService.csView(csNo);
    }


    //수정(수정할 데이터 불러오기)
    @GetMapping("/cs/modify")
    public ResponseEntity<?>csModify(@RequestParam("csNo")int csNo){

        log.info("수정할 번호 csNo : "+csNo);

        return  csService.csView(csNo);
    }

    //수정(데이터 수정하기)
    @PostMapping("/cs/modify")
    public ResponseEntity<?>csModify(@RequestBody CsDTO csDTO){

        log.info("수정할 데이터를 가지고 왔지!!! csDTO : "+csDTO);

        return csService.csModify(csDTO);
    }

    //삭제(게시글+게시글 답변)
    @GetMapping("/cs/delete")
    public ResponseEntity<?> csDelete(@RequestParam("csNo")int csNo){

        log.info("csNo : "+csNo);

        return csService.csDelete(csNo);
    }

    //관리자의 게시글 답변
    @PostMapping("/cs/answer")
    public ResponseEntity<?> csAnswer(@RequestBody CsCommentDTO csCommentDTO){

        log.info("관리자의 게시글 답변  - csCommentDTO : "+csCommentDTO);

        return csService.csAnswer(csCommentDTO);
    }

    @PostMapping("/cs/search")
    public ResponseEntity<?> search(@RequestBody PageRequestDTO pageRequestDTO){

        log.info("자 검색을 시작하러 가볼까?");

        log.info("이제 search쪽으로 와야제 ? pageRequestDTO: {}", pageRequestDTO);

        PageResponseDTO<?> searchResult = csService.search(pageRequestDTO);

        log.info("자 뭐가 나왔는지 볼까? : "+searchResult.toString());

        Map<String, String> map = new HashMap<>();
        map.put("list", "되니?");

        return ResponseEntity.status(HttpStatus.OK).body(map);

    }
}
