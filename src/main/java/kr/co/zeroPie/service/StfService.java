package kr.co.zeroPie.service;


import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.DptRepository;
import kr.co.zeroPie.repository.StfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class StfService {

    //private final PasswordEncoder passwordEncoder;
    private final StfRepository stfRepository;
    private final DptRepository dptRepository;
    private final ModelMapper modelMapper;


    @Value("${file.upload.path}")
    private String fileUploadPath;

    public ResponseEntity<?> register(StfDTO stfDTO) {//암호화 하지않고 저장, 이미지파일이 DTO에 저장되어 있음.
        //String encoded = passwordEncoder.encode(stfDTO.getStfPass());
        //stfDTO.setStfPass(encoded);

        Map<String, Object> map = new HashMap<>();
        try {
            Stf stf = modelMapper.map(stfDTO, Stf.class);

            MultipartFile img1 = stfDTO.getThumbFile();
            if (img1.getOriginalFilename() != null && img1.getOriginalFilename() != "") {
                StfDTO uploadedImage = uploadReviewImage(img1);
                log.info("여기는 첫번째 if.......");

                if (uploadedImage != null) {

                    StfDTO imageDTO = uploadedImage;

                    stf.setStfImg(uploadedImage.getSName());

                    log.info("여기는 2번째 if.......");
                }
                switch (stfDTO.getStrDptNo()){

                    case "인사지원부": stfDTO.setDptNo(1);
                        break;
                    case "영업부": stfDTO.setDptNo(2);
                        break;
                    case "전산부": stfDTO.setDptNo(3);
                        break;
                    case "네트워크 관리부": stfDTO.setDptNo(4);
                        break;
                    case "SW 개발 부서": stfDTO.setDptNo(5);
                        break;
                    case "고객 지원 부서": stfDTO.setDptNo(6);
                        break;
                    case "기술 지원부": stfDTO.setDptNo(7);
                        break;
                    default: stfDTO.setDptNo(0);
                        break;
                }

                switch (stfDTO.getStrRnkNo()){

                    case "사원": stfDTO.setDptNo(1);
                        break;
                    case "대리": stfDTO.setDptNo(2);
                        break;
                    case "과장": stfDTO.setDptNo(3);
                        break;
                    case "차장": stfDTO.setDptNo(4);
                        break;
                    case "부장": stfDTO.setDptNo(5);
                        break;
                    case "이사": stfDTO.setDptNo(6);
                        break;
                    case "상무": stfDTO.setDptNo(7);
                        break;
                    case "전무": stfDTO.setDptNo(8);
                        break;
                    case "사장": stfDTO.setDptNo(9);
                        break;
                    default: stfDTO.setDptNo(0);
                        break;
                }

                String id = null;

                switch (stfDTO.getDptNo())//아이디를 여기서 만들어서 넣음
                {
                    case 1:
                        id = "HR" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 2:
                        id = "SD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 3:
                        id = "ITD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 4:
                        id = "NMD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 5:
                        id = "SDD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 6:
                        id = "CSD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    case 7:
                        id = "TSD" + randomNumber();
                        stfDTO.setStfNo(id);
                        break;
                    default:
                        stfDTO.setStfNo(null);
                        break;
                }

                Stf stf1 = modelMapper.map(stfDTO, Stf.class);

                log.info("stf : " + stf);

                Stf savedUser = stfRepository.save(stf1);

                log.info("save 이후 : " + savedUser);

                StfDTO stfDTO1 = uploadedImage;//파일 저장경로 설정

                map.put("data", stfDTO1);//
                return ResponseEntity.ok().body(map);
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
        return ResponseEntity.ok().body(null);
    }


    public String randomNumber() {
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return Integer.toString(randomNumber);
    }


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
                        .size(140, 140)//여기를 size에서 forceSize로 강제 사이즈 변환
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
    public List<Dpt> findPosition(){


        List<Dpt> findPosition = dptRepository.findAll();//부서목록을 모두 출력

        return findPosition;
    }
}
