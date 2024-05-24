package kr.co.zeroPie.controller;



import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.service.StfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StfController {

    //private final StfService stfService;

    @PostMapping("/upload")
    public ResponseEntity<?> register(StfDTO stfDTO){
        log.info("파일이 들어왔네?");

        log.info("stfDTO 출력 : "+stfDTO);

        log.info("사진 잘 들어옴? : "+stfDTO.getThumbFile());

        return ResponseEntity.ok("성공!");
    }
}
