package kr.co.zeroPie.controller;

import kr.co.zeroPie.dto.DocDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DocController {


    @PostMapping("/doc/save")
    public ResponseEntity<?> docSave(List<DocDTO> docDTOList) {

        log.info("docDTOList : " + docDTOList);


        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

}
