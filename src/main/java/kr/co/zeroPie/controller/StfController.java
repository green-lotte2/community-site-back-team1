package kr.co.zeroPie.controller;


import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.service.StfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StfController {

    private final StfService stfService;

    @PostMapping("/user")
    public StfDTO register(@RequestBody StfDTO stfDTO){

        log.info(stfDTO.toString());

        String uid = stfService.register(stfDTO);
        //return Map.of("userid", uid);

        log.info("uid : " + uid);
        return stfDTO;
    }

}
