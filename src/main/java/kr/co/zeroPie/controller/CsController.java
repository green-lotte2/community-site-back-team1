package kr.co.zeroPie.controller;


import kr.co.zeroPie.dto.CsDTO;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.dto.PageResponseDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.service.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CsController {

    private final CsService csService;


    @GetMapping("/cs/list")
    public ResponseEntity<?> csList(PageRequestDTO pageRequestDTO){//문의하기 리스트 띄우기
        log.info("문의하기 리스트 띄웁니다.");
        log.info("pageRequestDTO: {}", pageRequestDTO);
        return csService.list(pageRequestDTO);
    }

}
