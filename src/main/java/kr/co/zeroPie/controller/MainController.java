package kr.co.zeroPie.controller;

import kr.co.zeroPie.dto.CsDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.repository.CsRepository;
import kr.co.zeroPie.service.CsService;
import kr.co.zeroPie.service.StfService;
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
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final StfService stfService;
    private final CsService csService;

    @PostMapping("/main")
    public ResponseEntity<?> mainInfo(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        // 한번에 반환시킬 Map
        Map<String, List<?>> resultMap = new HashMap<>();

        // 공지사항 조회
        resultMap.put("csDTO", csService.selectCsForMain());

        // 개인 정보 조회
        resultMap.put("stfDTO", stfService.selectStfInfo(userId));

        // 생일 정보 조회
        resultMap.put("birthDTO", stfService.selectStfForBrith());

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

}
