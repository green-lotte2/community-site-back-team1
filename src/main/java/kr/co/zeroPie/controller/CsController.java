package kr.co.zeroPie.controller;


import kr.co.zeroPie.dto.*;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.CsComment;
import kr.co.zeroPie.service.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
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
    public ResponseEntity<?> csList(@RequestBody PageRequestDTO pageRequestDTO) {


        log.info("문의하기 리스트 띄웁니다.");
        log.info("받아온고니 ?  : pageRequestDTO: {}", pageRequestDTO);

        ResponseEntity<?> csList = null;

        log.info("자 검색을 시작하러 가볼까?");

        log.info("이제 search쪽으로 와야제 ? pageRequestDTO: {}", pageRequestDTO);

        csList = csService.list(pageRequestDTO);

        log.info("자 여기는 거의 끝나는 지점 - list");

        log.info("여기는 컨트롤러에 있는 csList: {}", csList.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(csList.getBody());
    }


    //고객센터 검색 리스트 출력
    @PostMapping("/cs/search")///////////새로 생김////////////////////////
    public ResponseEntity<?> csSearch(@RequestBody PageRequestDTO pageRequestDTO) {


        log.info("문의하기 리스트 띄웁니다.");
        log.info("받아온고니 ?  : pageRequestDTO: {}", pageRequestDTO);

        ResponseEntity<?> csList = null;

        log.info("자 검색을 시작하러 가볼까?");

        log.info("이제 search쪽으로 와야제 ? pageRequestDTO: {}", pageRequestDTO);

        csList = csService.search(pageRequestDTO);

        log.info("자 여기는 거의 끝나는 지점 - search");

        log.info("여기는 컨트롤러에 있는 csList: {}", csList.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(csList.getBody());
    }





    //등록
    @PostMapping("/cs/register")
    public ResponseEntity<?> csRegister(@RequestBody CsDTO csDTO) {

        log.info("문의하기 등록입니다.");
        log.info("csDTO: {}", csDTO);

        csService.csRegister(csDTO);

        return ResponseEntity.status(HttpStatus.OK).body(1);//상태.데이터(왼쪽 순으로)
    }

    //보기
    @GetMapping("/cs/view")
    public ResponseEntity<?> csView(@RequestParam("csNo") int csNo) {

        log.info("보기위해 값 들고오기 - csNo : " + csNo);

        return csService.csView(csNo);

    }


    //수정(수정할 데이터 불러오기)
    @GetMapping("/cs/modify")
    public ResponseEntity<?> csModify(@RequestParam("csNo") int csNo) {

        log.info("수정할 번호 csNo : " + csNo);

        return csService.csView(csNo);
    }
/*
    //수정(데이터 수정하기)
    @PostMapping("/cs/modify")
    public ResponseEntity<?> csModify(@RequestBody CsDTO csDTO) {

        log.info("수정할 데이터를 가지고 왔지!!! csDTO : " + csDTO);

        return csService.csModify(csDTO);
    }

 */

    //삭제(게시글+게시글 답변)
    @GetMapping("/cs/delete")
    public ResponseEntity<?> csDelete(@RequestParam("csNo") int csNo) {

        log.info("csNo : " + csNo);

        return csService.csDelete(csNo);
    }

    //관리자의 게시글 답변
    @PostMapping("/cs/answer")
    public ResponseEntity<?> csAnswer(@RequestBody CsCommentDTO csCommentDTO) {

        log.info("관리자의 게시글 답변  - csCommentDTO : " + csCommentDTO);

        return csService.csAnswer(csCommentDTO);
    }

    //댓글 불러오기
    @GetMapping("/cs/answerList")
    public ResponseEntity<?> csAnswerList(@RequestParam("csNo") int csNo) {

        log.info("댓글 목록을 불러옵니다 - csNo : " + csNo);

        return csService.csAnswerList(csNo);
    }

    //댓글 삭제
    @GetMapping("/cs/answerDelete")
    public ResponseEntity<?> answerDelete(@RequestParam("csComNo") int csComNo) {

        log.info("댓글 목록을 불러옵니다 - csComNo : " + csComNo);

        return csService.answerDelete(csComNo);//잘 삭제가 되면 1을 출력
    }

}
