package kr.co.zeroPie.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.PlanDTO;
import kr.co.zeroPie.dto.PlanOrderDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.*;
import kr.co.zeroPie.repository.StfRepository;
import kr.co.zeroPie.security.MyUserDetails;
import kr.co.zeroPie.service.StfService;
import kr.co.zeroPie.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StfController {

    private final StfService stfService;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    private final RedisTemplate<String, String> redisTemplate;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {

        log.info("login1........username : " + username);
        log.info("login1........password : " + password);

        try {
            // Security 인증 처리
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(username, password);


            // 사용자 DB 조회
            Authentication authentication = authenticationManager.authenticate(authToken);
            log.info("login...2");

            // 인증된 사용자 가져오기
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Stf user = userDetails.getStf();

            log.info("login...3 : " + user);

            // 회원 플랜 정보 조회
            int planState = stfService.selectStfPlan(user.getPlanStatusNo());
            

            // 토큰 발급(액세스, 리프레쉬)
            String access = jwtProvider.createToken(user, 1); // 1일
            String refresh = jwtProvider.createToken(user, 7); // 7일

            // 리프레쉬 토큰 DB 저장

            // 액세스 토큰 클라이언트 전송
            Map<String, Object> map = new HashMap<>();
            map.put("grantType", "Bearer");
            map.put("userId", user.getStfNo());
            map.put("username", user.getStfName());
            map.put("userEmail", user.getStfEmail());
            map.put("userImg", user.getStfImg());
            map.put("userRole", user.getStfRole());
            map.put("planState", planState);
            map.put("accessToken", access);
            map.put("refreshToken", refresh);

            return ResponseEntity.ok().body(map);

        } catch (Exception e) {
            log.info("login...3 : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        }
    }


    //회원가입 기능
    @PostMapping("/upload")
    public ResponseEntity<?> register(StfDTO stfDTO) {//파일업로드하는 동시에 아이디를 만들어서 no에 저장도 해야함!


        log.info("파일이 들어왔네?");

        log.info("stfDTO 출력 : " + stfDTO);

        log.info("사진 잘 들어옴? : " + stfDTO.getThumbFile());

        Stf result = stfService.register(stfDTO);

        log.info("아이디 출력 : " + result.getStfNo());

        Map<String, Object> response = new HashMap<>();
        response.put("stfNo", result.getStfNo());

        return ResponseEntity.ok(response);


        //return ResponseEntity.ok("성공!");
    }


    //부서 목록 찾기
    @GetMapping("/findPosition")
    public ResponseEntity<?> findPosition() {

        List<Dpt> findPositions = stfService.findPosition();

        log.info("findPosition : " + findPositions);
        Map<String, List<Dpt>> lists = new HashMap<>();
        lists.put("result", findPositions);

        return ResponseEntity.ok().body(lists);
    }

    //직급 목록 찾기
    @GetMapping("/findRnk")
    public ResponseEntity<?> findRnk() {

        List<Rnk> findRnks = stfService.findRnk();

        log.info("findRnk : " + findRnks);

        Map<String, List<Rnk>> lists = new HashMap<>();
        lists.put("result", findRnks);

        return ResponseEntity.ok().body(lists);
    }

    //이메일 인증 보내기
    @GetMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam("email") String email) {

        log.info("일단 들어오니?");

        log.info("email : " + email);

        int count = stfService.findStf(email);//같은 이메일이 몇개인지 체크

        log.info("count={}", count);

        Map<String, String> lists = new HashMap<>();

        if (count < 1) {//이메일 중복이 없어야됨
            log.info("email={}", email);
            long savedCode = stfService.sendEmailCode(email);

            lists.put("result", "성공");
            lists.put("savedCode", String.valueOf(savedCode));

            log.info("지금 이게 문제라 들어와야함 : "+String.valueOf(savedCode));


            return ResponseEntity.ok().body(lists);
        } else {

            lists.put("result", "실패");
            return ResponseEntity.ok().body(lists);
        }
    }

    //사용자가 입력한 코드와 서버에서 보낸코드가 맞는지 확인 기능
    @GetMapping("/verifyCode")
    public ResponseEntity<?> sendVerifyCode(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("scode") String scode) {

        log.info("여기까지 잘 들어왔어!");

        log.info("email : " + email);

        log.info("code : " + code);

        log.info("scode(서버에서 보내온 코드) : " + scode);

        Map<String, String> lists = new HashMap<>();

        //ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        //String savedCode = valueOperations.get("code1");

        long before = Long.parseLong(scode);//scode를 다시 long으로 변환

        // 복호화 과정
        long intermediate = (before + 1) / 2;
        long savedCode = (long) Math.sqrt(intermediate);

        String originCode = String.valueOf(savedCode);

        log.info("intermediate(복호화1번째 과정) : " + intermediate);

        log.info("originCode : " + originCode);

        if (originCode.equals(code)) {//originCode != null &&
            lists.put("result", "성공");
        } else {
            lists.put("result", "실패");
        }

        return ResponseEntity.ok().body(lists);

    }

    //회원 약관 출력 기능
    @GetMapping("/terms")
    public ResponseEntity<?> terms() {
        List<Terms> termsList = stfService.findTerms();

        String privacy = termsList.get(0).getPrivacy();
        String terms = termsList.get(0).getTerms();


        Map<String, String> lists = new HashMap<>();
        lists.put("result1", privacy);
        lists.put("result2", terms);

        return ResponseEntity.ok().body(lists);
    }


    //아이디 찾기에서 이메일 인증 보내기
    @GetMapping("/findIdAndSendEmail")
    public ResponseEntity<?> findIdAndSendEmail(@RequestParam("email") String email,@RequestParam("name")String name) {


        log.info("일단 들어오니?");

        log.info("email : " + email);
        log.info("name : " + name);


        int count = stfService.findStfUseFindId(email,name);//가입한 사용자인지 체크


        log.info("count={}", count);

        Map<String, String> lists = new HashMap<>();

        if (count >= 1) {//이메일 중복이 없어야됨
            log.info("email={}", email);
            long savedCode = stfService.sendEmailCode(email);//이메일을 보내고 서버가 보낸 코드를 저장

            lists.put("result", "성공");
            lists.put("savedCode", String.valueOf(savedCode));
            return ResponseEntity.ok().body(lists);
        } else {

            lists.put("result", "실패");
            return ResponseEntity.ok().body(lists);
        }
    }

     //아이디 찾기에서 이메일 인증 보내기
    @GetMapping("/findPassAndSendEmail")
    public ResponseEntity<?> findPassAndSendEmail(@RequestParam("email") String email,@RequestParam("id")String id) {


        log.info("일단 들어오니?");

        log.info("email : " + email);

        int count = stfService.findStfUseFindPass(email,id);//가입한 사용자인지 체크

        log.info("count={}", count);

        Map<String, String> lists = new HashMap<>();

        if (count >= 1) {//가입한 사용자
            log.info("email={}", email);
            long savedCode = stfService.sendEmailCode(email);//이메일을 보내고 서버가 보낸 코드를 저장

            lists.put("result", "성공");
            lists.put("savedCode", String.valueOf(savedCode));
            return ResponseEntity.ok().body(lists);
        } else {

            lists.put("result", "실패");
            return ResponseEntity.ok().body(lists);
        }
    }

    //아이디 찾기
    @GetMapping("/findId")
    public ResponseEntity<?> findId(@RequestParam("email")String email, @RequestParam("name")String name){

        log.info("email1 : "+email);
        log.info("name1 : "+name);

        String id = stfService.findId(email,name);

        log.info("id : "+id);

        Map<String, String> lists = new HashMap<>();

        lists.put("result", id);
        return ResponseEntity.ok().body(lists);

    }

    //비밀번호 변경
    @PostMapping("/updatePass")
    public ResponseEntity<?> updatePass(StfDTO stfDTO){

        log.info("stfDTO( 아이디와 비밀번호가 넘어와야해 ) :"+stfDTO);

        String id = stfDTO.getStfNo();

        String pass = stfDTO.getStfPass();

        stfService.updatePass(id, pass);

        Map<String, String> lists = new HashMap<>();

        lists.put("result", "되는가?");
        return ResponseEntity.ok().body(lists);
    }
    
    //플랜 리스트 들고오기
    @GetMapping("/getPlan")
    public ResponseEntity<?> getPlan(){

        log.info("stfController.getPlan() 들어옴");

        List<PlanDTO> plan = stfService.getPlan();

        log.info(plan.toString());

        return ResponseEntity.ok().body(plan);
    }


    //사용자 정보 가져오기
    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestParam("stfNo")String stfNo){

        log.info("아이디 출력 : "+stfNo);

        StfDTO stfDTO = stfService.getUserInfo(stfNo);

        return  ResponseEntity.ok().body(stfDTO);
    }

    //"기본값 * 유저 수"를 출력하기 위해 유저수를 구함
    @GetMapping("/getCountUser")
    public ResponseEntity<?> getCountUser(){

        log.info("유저 구하기");

        long count = stfService.getCountUser();

        log.info("controller - getCountUser - count : "+count);

        return ResponseEntity.ok().body(count);
    }

    //플랜 결제
    @PostMapping("/postPay")
    public ResponseEntity<?> postPay(@RequestBody PlanOrderDTO planOrderDTO){

        log.info("controller - postPay - planOrderDTO : "+planOrderDTO);

        int planNo = stfService.planOrder(planOrderDTO);

        return ResponseEntity.ok().body(planNo);

    }

    //선택한 플랜 저장
    @GetMapping("/savePlan")
    public ResponseEntity<?> savePlan(@RequestParam("user")String user, @RequestParam("planNo")int planNo){

        log.info("controller - savePlan - user : "+user);
        log.info("controller - planType : "+planNo);

        stfService.savePlan(user,planNo);

        return ResponseEntity.ok().body(1);

    }

    //무료플랜
    @GetMapping("/freePlan")
    public ResponseEntity<?> freePlan(@RequestParam("stfNo")String stfNo){

         return stfService.freePlan(stfNo);

    }
    
    //관리자 플랜 구하기
    @GetMapping("/getPlanStatusNo")
    public ResponseEntity<?> getPlanStatusNo(){

       return stfService.findAdminPlan();

    }

    // 전화번호 중복 검사
    @PostMapping("/checkPh")
    public ResponseEntity<?> checkPh(@RequestBody Map<String, String> request) {
        String stfPh = request.get("stfPh");
        return stfService.checkStfPh(stfPh);
    }
}
