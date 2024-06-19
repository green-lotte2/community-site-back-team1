package kr.co.zeroPie.oauth2;

import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.service.StfService;
import kr.co.zeroPie.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2TokenController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final KakaoTokenService kakaoTokenService;
    private final StfService stfService;
    private final JWTProvider jwtProvider;
    private final ModelMapper modelMapper;
    private final HttpSession httpSession;

    @GetMapping("/auth")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
        // 카카오 인증 코드로부터 액세스 토큰을 받아옴
        log.info("code {}", code);

        String kakaoAccessToken = kakaoTokenService.getAccessToken(code);

        Map<String, Object> StfMap = kakaoTokenService.getStf(kakaoAccessToken);
        String StfEmail = (String) StfMap.get("email");
        String nick = (String) ((Map<String, Object>) StfMap.get("profile")).get("nickname");
        LocalDate currentDate = LocalDate.now();

        log.info("StfMap체크 {}", StfMap);
        log.info("Stfnick체크 {}", nick);
        log.info("Kakao access token: {}", kakaoAccessToken);
        log.info("Kakao access StfEmail: {}", StfEmail);

        // 데이터베이스에서 사용자 정보 조회 또는 등록
        Stf stf = stfService.findByUid(StfEmail);
        if (stf == null) {
            stf = Stf.builder()
                    .stfNo(StfEmail)
                    //.stfPass("kakao")
                    .stfName(nick)
                    .stfEmail(StfEmail)
                    .stfRole("USER")
                    .stfEnt(currentDate)
                    .stfStatus("Active")
                    .dptNo(1)
                    .rnkNo(1)
                    .planStatusNo(1)
                    // 추가 필드 설정
                    .build();
            stf = stfService.kakaoRegister(modelMapper.map(stf, StfDTO.class));

            log.info("stf 찍혀라잉 : " + stf);
        }

        // 세션에 사용자 정보 저장 (예시로 HttpSession을 사용)
        httpSession.setAttribute("Stf", stf);

        String accessToken = jwtProvider.createToken(stf, 1);

        log.info("accessToken : {}", accessToken);
        // 받아온 액세스 토큰을 사용하여 원하는 동작을 수행하거나 다른 서비스로 전달
        // 여기에서는 단순히 액세스 토큰을 반환함

        Map<String, Object> map = new HashMap<>();
        map.put("grantType", "Bearer");
        map.put("userId", stf.getStfNo());
        map.put("username", stf.getStfName());
        map.put("userEmail", stf.getStfEmail());
        //map.put("hp", stf.getStfPh());
        map.put("userRole", stf.getStfRole());
        map.put("planState", stf.getPlanStatusNo());
        map.put("profile", stf.getStfImg());
        map.put("rdate", stf.getStfEnt());
       // map.put("department", stf.getDptNo());
        //map.put("position", stf.getRnkNo());
        map.put("accessToken", accessToken);

        log.info("map : {}", map);

        return ResponseEntity.ok().body(map);

    }
}
