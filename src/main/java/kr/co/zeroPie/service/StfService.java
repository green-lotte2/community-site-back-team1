package kr.co.zeroPie.service;


import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.PlanDTO;
import kr.co.zeroPie.dto.PlanOrderDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.dto.ToDoDTO;
import kr.co.zeroPie.entity.*;
import kr.co.zeroPie.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class StfService {

    private final PasswordEncoder passwordEncoder;
    private final StfRepository stfRepository;
    private final DptRepository dptRepository;
    private final RnkRepository rnkRepository;
    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;
    private final PlanRepository planRepository;
    private final PlanOrderRepository planOrderRepository;
    private final PlanStatusRepository planStatusRepository;
    private final TodoRepository todoRepository;

    private final JavaMailSender javaMailSender;



    @Value("${file.upload.path}")
    private String fileUploadPath;

    //회원 가입기능
    public Stf register(StfDTO stfDTO) {

        if (stfDTO.getStfPass() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        log.info("");

        String encoded = passwordEncoder.encode(stfDTO.getStfPass());
        stfDTO.setStfPass(encoded);

        Stf stf = modelMapper.map(stfDTO, Stf.class);

        Map<String, Object> map = new HashMap<>();
        try {
            MultipartFile img1 = stfDTO.getThumbFile();
            if (img1.getOriginalFilename() != null && img1.getOriginalFilename() != "") {
                StfDTO uploadedImage = uploadReviewImage(img1);
                log.info("여기는 첫번째 if.......");

                if (uploadedImage != null) {

                    StfDTO imageDTO = uploadedImage;

                    stfDTO.setSName(uploadedImage.getSName());
                    stfDTO.setStfImg(uploadedImage.getSName());

                    log.info("여기는 2번째 if.......");
                    log.info("imageDTO의 sName : " + stfDTO.getSName());
                    log.info("imageDTO의 stfImg : " + stfDTO.getStfImg());
                }

                Dpt compleDtp = stfRepository.dptSelect(stfDTO);//선택한 부서에 맞는 db에 저장된 부서값

                String idCode = compleDtp.getDptCode();//아이디가 될 코드

                //Rnk compleRnk = stfRepository.rnkSelect(stfDTO);

                String id = idCode+ randomNumber();

                stfDTO.setStfNo(id);


                log.info("stfDTO : " + stfDTO);

                Stf stf1 = modelMapper.map(stfDTO, Stf.class);

                log.info("stf1 : " + stf1);

                //stf1.setPlanStatusNo(1);

                stf1.setStfStatus("Active");

                Stf savedUser = stfRepository.save(stf1);

                log.info("save 이후 : " + savedUser);

                StfDTO stfDTO1 = uploadedImage;//파일 저장경로 설정

                return savedUser;
            }
        } catch (Exception e) {
            return stf;
        }
        return stf;
    }


    //아이디 뒤에 붙는 랜덤숫자 출력해주는곳
    public String randomNumber() {
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return Integer.toString(randomNumber);
    }


    //프로필 이미지 저장
    public StfDTO uploadReviewImage(MultipartFile file) {
        // 파일을 저장할 경로 설정

        String path = new java.io.File(fileUploadPath).getAbsolutePath();

        if (!file.isEmpty()) {
            try {
                // 원본 파일 이름과 확장자 추출
                String originalFileName = file.getOriginalFilename();//원본 파일 네임
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                log.info("uploadReviewImage - originalFileName :  잘 들어오나요? : " + originalFileName);

                // 저장될 파일 이름 생성
                String sName = UUID.randomUUID().toString() + extension;//변환된 파일 이름


                log.info("파일 변환 후 이름 - sName : " + sName);

                // 파일 저장 경로 설정
                java.io.File dest = new File(path, sName);

                Thumbnails.of(file.getInputStream())
                        .size(200, 200)//여기를 size에서 forceSize로 강제 사이즈 변환
                        .toFile(dest);


                log.info("service - dest : " + dest);

                // 리뷰이미지 정보를 담은 DTO 생성 및 반환
                return StfDTO.builder()
                        .oName(originalFileName)
                        .sName(sName)
                        .build();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // 업로드 실패 시 null 반환
    }


    //부서목록 return
    public List<Dpt> findPosition() {


        List<Dpt> findPosition = dptRepository.findAll();//부서목록을 모두 출력

        return findPosition;
    }

    //부서목록 return
    public List<Rnk> findRnk() {

        List<Rnk> findRnk = rnkRepository.findAll();//부서목록을 모두 출력

        return findRnk;
    }

    //email이 중복인지 체크(같은 이메일이 몇개인지 체크)
    public int findStf(String email) {

        return stfRepository.countByStfEmail(email);
    }
    ///////추가//////////////////////////////

    //이름과 이메일을 이용해서 가입한 사용자인지 체크
    public int findStfUseFindId(String email,String name) {

        return stfRepository.countByStfEmailAndStfName(email,name);
    }

    //아이디와 이메일을 이용해서 가입한 사용자인지 체크
    public int findStfUseFindPass(String email,String id) {

        return stfRepository.countByStfEmailAndStfNo(email,id);
    }


////////////////////추가 끝!///////////////////////

    //이메일 보내기 서비스
    @Value("${spring.mail.username}")//이메일 보내는 사람 주소
    private String sender;

    //인증코드를 전송
    public long sendEmailCode(String receiver) {//void->int

        log.info("sender={}", sender);

        //MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        //인증코드 생성 후 세션 저장
        String code12 = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        int code = ThreadLocalRandom.current().nextInt(100000, 1000000);

        log.info("생성된 code : " + code);


        //redis 사용
        //ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        //valueOperations.set("code1",code12);
        long savedCode = ((long) (code + code) * code) - 1; // long 타입으로 변경(숫자가 너무커서 오버플로우가 일어남(-로 표기))

        log.info("savedCode={}", savedCode);

        String title = "zeroPie 인증코드 입니다.";
        String content = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f4f4f4;"
                + "            margin: 0;"
                + "            padding: 0;"
                + "        }"
                + "        .container {"
                + "            background-color: #ffffff;"
                + "            max-width: 600px;"
                + "            margin: 20px auto;"
                + "            padding: 20px;"
                + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);"
                + "        }"
                + "        .header {"
                + "            background-color: #007bff;"
                + "            color: #ffffff;"
                + "            padding: 10px 20px;"
                + "            text-align: center;"
                + "        }"
                + "        .content {"
                + "            padding: 20px;"
                + "            text-align: center;"
                + "        }"
                + "        .code {"
                + "            font-size: 24px;"
                + "            font-weight: bold;"
                + "            color: #007bff;"
                + "        }"
                + "        .footer {"
                + "            margin-top: 20px;"
                + "            font-size: 12px;"
                + "            color: #888888;"
                + "            text-align: center;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class='container'>"
                + "        <div class='header'>"
                + "            <h1>인증코드 안내</h1>"
                + "        </div>"
                + "        <div class='content'>"
                + "            <p>안녕하세요, zeroPie 에 오신걸 환영합니다.</p>"
                + "            <p>인증코드는 다음과 같습니다:</p>"
                + "            <p class='code'>" + code + "</p>"
                + "            <p>이 코드를 사용하여 인증을 완료해 주세요.</p>"
                + "        </div>"
                + "        <div class='footer'>"
                + "            <p>이 메일은 자동으로 생성된 메일입니다. 회신하지 말아주세요.</p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);

            log.info("sendEmailCode - 여기서 한 번 더 찍어보자 : " +savedCode);

            return savedCode;

        } catch (Exception e) {
            log.error("error={}", e.getMessage());

            return 0;
        }
    }

    //약관 찾기
    public List<Terms> findTerms(){

        List<Terms> termsList= termsRepository.findAll();

        return termsList;

    }
    
    //아이디 찾기
    public String findId(String email,String name){

    log.info("email2 : "+email);
    log.info("name2 : "+name);

    Stf stf =  stfRepository.findIdByStfEmailAndStfName(email,name);

    log.info("아이디 : "+stf.getStfNo());

    return stf.getStfNo();
    
}

//비밀번호 변경
public void updatePass(String id, String pass){

    String encoded = passwordEncoder.encode(pass);

    log.info("비밀번호 변경 id가 들어와? : "+id);
    log.info("비밀번호 변경 pass가 들어와? : "+pass);

    Optional<Stf> stfOpt = stfRepository.findById(id);

    Stf stf= modelMapper.map(stfOpt,Stf.class);

    log.info("비밀번호 수정전에 한번 출력 : "+stf);

    stf.setStfPass(encoded);//비밀번호 변경

    stfRepository.save(stf);

    }

    //플랜 들고오기
    public List<PlanDTO> getPlan(){
        
        log.info("StfService - getPlan 들어옴");

        List<Plan> plans = planRepository.findAll();

        List<PlanDTO> dtoList = plans.stream()
                .map(entity -> {
                    PlanDTO dto = modelMapper.map(entity, PlanDTO.class);
                    return dto;
                })
                .toList();

        return dtoList;

    }
    
    //플랜에 필요한 유저 정보 가져오기
    public StfDTO getUserInfo(String stfNo){

        Optional<Stf> userInfo = stfRepository.findById(stfNo);

        StfDTO stfDTO = modelMapper.map(userInfo,StfDTO.class);

        log.info("stfService - getUserInfo - stfDTO "+stfDTO.toString());

        return stfDTO;
    }

    //유저 수
    public long getCountUser(){

        long count = stfRepository.countByUser();

        return count;

    }

    //플랜 결제
    @Transactional
    public int planOrder(PlanOrderDTO planOrderDTO){

        log.info("stfService - PlanOrderDTO : "+planOrderDTO);

        PlanStatus planStatus = new PlanStatus(planOrderDTO.getPlanNo());

        planStatus.setPlanEdate();

        planStatusRepository.save(planStatus);//먼저 PlanStatus테이블에 저장

        log.info("저장한 planStatus 상태 보기: "+planStatus);

        planOrderDTO.setPlanStatusNo(planStatus.getPlanStatusNo());

        planOrderRepository.save(modelMapper.map(planOrderDTO,PlanOrder.class));//planOrder 테이블에 PlanStatusNo 넣어주고 저장

        log.info("stfService -planOrder- planOrderDTO"+planOrderDTO);

        return planStatus.getPlanStatusNo();

    }

    // 로그인 토큰 발급시 사용자 플랜 정보 조회하기
    public int selectStfPlan(int planStatusNo) {
        Optional<PlanStatus> optPlanStatus = planStatusRepository.findById(planStatusNo);
        if (optPlanStatus.isPresent()) {
            Date planEndDate = optPlanStatus.get().getPlanEdate();
            Date currentDate = new Date();

            LocalDateTime planEndLocalDateTime = planEndDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDateTime currentLocalDateTime = currentDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            log.info("플랜 : " + planEndLocalDateTime);
            log.info("지금 : " + currentLocalDateTime);
            if (planEndLocalDateTime.isAfter(currentLocalDateTime)) {
                return optPlanStatus.get().getPlanNo();
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }


    //내가 가입한 플랜을 저장(무료 제외)
    public void savePlan(String user,int planNo){

        log.info("내가 가입한 플랜 번호 : "+planNo);

        Optional<Stf> optUser = stfRepository.findById(user);

        Stf stf = modelMapper.map(optUser,Stf.class);//아이디에 해당하는 유저를 찾아서 플랜을 심어줌.

        stf.setPlanStatusNo(planNo);

        stfRepository.save(stf);

        //여기서부터 회원가입이 되어 있는 사용자들 플랜정보 변경해주기(planNo로 바꿔주기)

        List<Stf> userList = stfRepository.findAll();

        log.info("플랜 변경할 userList 보기 : "+userList);

        for(Stf change:userList){
            change.setPlanStatusNo(planNo);
            stfRepository.save(change);
        }
        //여기까지 변경 완료
    }


    //무료 플랜을 선택하면 결제 없이 바로 저장(+회원 가입이 다 되어있는 사용자들도 변경해주기)
    public ResponseEntity<?> freePlan(String stfNo) {
        PlanStatus planStatus = new PlanStatus(1);

        planStatus.setPlanEdate();

        planStatusRepository.save(planStatus);

        int planNo = planStatus.getPlanStatusNo();//플랜

        Optional<Stf> optStf = stfRepository.findById(stfNo);

        Stf stf = modelMapper.map(optStf, Stf.class);

        stf.setPlanStatusNo(planNo);

        stfRepository.save(stf);

        //여기서부터 회원가입이 되어 있는 사용자들 플랜정보 변경해주기(planNo로 바꿔주기)

        List<Stf> userList = stfRepository.findAll();

        log.info("플랜 변경할 userList 보기 : "+userList);

        for(Stf change:userList){
            change.setPlanStatusNo(planNo);
            stfRepository.save(change);
        }
        //여기까지 변경 완료

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    
    // 메인페이지 출력용 회원 정보 조회
    public List<StfDTO> selectStfInfo(String stfNo) {

        Optional<Stf> optStf = stfRepository.findById(stfNo);

        List<StfDTO> stfDTOList = new ArrayList<>();
        if(optStf.isPresent()) {
            StfDTO stfDTO = modelMapper.map(optStf, StfDTO.class);
            Optional<Dpt> optDpt = dptRepository.findById(optStf.get().getDptNo());
            Optional<Rnk> optRnk = rnkRepository.findById(optStf.get().getRnkNo());
            Optional<PlanStatus> optPlan = planStatusRepository.findById(optStf.get().getPlanStatusNo());
            stfDTO.setStrDptName(optDpt.get().getDptName());
            stfDTO.setStrRnkNo(optRnk.get().getRnkName());
            stfDTO.setPlanNo(optPlan.get().getPlanNo());
            stfDTOList.add(stfDTO);
            return stfDTOList;
        }
        return null;
    }

    // 메인페이지 출력용 생일자 조회
    public List<StfDTO> selectStfForBrith() {
        List<Stf> stfList = stfRepository.findByStfBirth(LocalDate.now());
        return stfList.stream()
                .map(stf -> {
                    StfDTO each = modelMapper.map(stf, StfDTO.class);
                    Optional<Dpt> optDpt = dptRepository.findById(each.getDptNo());
                    Optional<Rnk> optRnk = rnkRepository.findById(each.getRnkNo());
                    each.setStrDptName(optDpt.get().getDptName());
                    each.setStrRnkNo(optRnk.get().getRnkName());
                    return each;
                }).toList();
    }

    // 전화 번호 중복 검사
    public ResponseEntity<?> checkStfPh(String stfPh) {
      Optional<Stf> stf = stfRepository.findByStfPh(stfPh);

            if (stf.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(1);
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(0);
            }
    }
    
    
    //관리자 플랜 찾기
    public ResponseEntity<?> findAdminPlan(){

       List<Stf> adminPlan=  stfRepository.findStfRole();

       if(!adminPlan.isEmpty()) {
           Stf first = adminPlan.get(0);

           log.info("첫 번째 Admin 정보 : "+first);

           return ResponseEntity.status(HttpStatus.OK).body(first.getPlanStatusNo());
       }

       return null;

    }

    //oauth에 사용됨
    public Stf findByUid(String uid){
        return stfRepository.findByStfNo(uid);
    }


    //카카오전용 회원가입

    public Stf kakaoRegister(StfDTO stfDTO){

        /*
        if (stfDTO.getStfPass() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        log.info("");*/
        if (stfDTO.getStfPass() == null) {
            String encoded = passwordEncoder.encode("kakao");
            stfDTO.setStfPass(encoded);
        }
        Stf stf = modelMapper.map(stfDTO, Stf.class);
        Stf savedStf = stfRepository.save(stf);

        return savedStf;

    }


    // 메인 페이지 todo 목록 조회
    public List<ToDoDTO> selectStfTodo(String stfNo) {
        List<ToDo> todoList = todoRepository.findByStfNoAndTodoStatusOrderByTodoDateDesc(stfNo, "Y");
        return todoList.stream().map(each -> modelMapper.map(each, ToDoDTO.class)).toList();
    }
}
