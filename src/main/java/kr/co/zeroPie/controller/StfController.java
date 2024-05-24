package kr.co.zeroPie.controller;



import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.service.StfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StfController {

    private final StfService stfService;

    @PostMapping("/upload")
    public ResponseEntity<?> register(StfDTO stfDTO){//파일업로드하는 동시에 아이디를 만들어서 no에 저장도 해야함!
        log.info("파일이 들어왔네?");

        log.info("stfDTO 출력 : "+stfDTO);

        log.info("사진 잘 들어옴? : "+stfDTO.getThumbFile());//들어왔음!!!!


        stfService.register(stfDTO);

        return ResponseEntity.ok("성공!");
    }


    @GetMapping("/findPosition")
    public List<Dpt> findPosition(){

        List<Dpt> findPosition = stfService.findPosition();

        log.info("findPosition : "+findPosition);

        return findPosition;
    }
}
