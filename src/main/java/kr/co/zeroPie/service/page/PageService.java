package kr.co.zeroPie.service.page;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.PageDTO;
import kr.co.zeroPie.entity.MyDoc;
import kr.co.zeroPie.entity.Page;
import kr.co.zeroPie.repository.MyDocRepository;
import kr.co.zeroPie.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PageService {

    private final PageRepository pageRepository;
    private final MyDocRepository myDocRepository;
    private final ModelMapper modelMapper;

    // 문서 목록 불러오기
    public ResponseEntity<?> selectPageList (String userId) {
        String modifiedUserId = userId.substring(1, userId.length() - 1);
        List<Tuple> resultList = pageRepository.selectPageListById(modifiedUserId);

        List<Map<String, Object>> pageList = new ArrayList<>();
        for (Tuple result : resultList) {
            Map<String, Object> pageInfo = new HashMap<>();
            pageInfo.put("pno", result.get(0, Integer.class));
            pageInfo.put("title", result.get(1, String.class));
            pageInfo.put("owner", result.get(2, String.class));
            pageList.add(pageInfo);
        }

        log.info("pageList : " + pageList);

        return ResponseEntity.status(HttpStatus.OK).body(pageList);
    }

    // 페이지 데이터 저장
    public void insertPageContent (PageDTO pageDTO) {
        log.info("PageService : " + pageDTO);

        if (pageDTO.getRDate() == null) {
            pageDTO.setRDate(LocalDateTime.now());
        }
        pageRepository.save(modelMapper.map(pageDTO, Page.class));

        Optional<Page> savedPage = pageRepository.findById(pageDTO.getPno());

        if (savedPage.isPresent()) {
            log.info("저장한거 : " + savedPage.get());
        }
    }

    // 페이지 데이터 불러오기
    public ResponseEntity<?> selectPageContent (int pno) {

        Optional<Page> savedPage = pageRepository.findById(pno);

        if (savedPage.isPresent()) {
            log.info("" + savedPage.get());
            return ResponseEntity.status(HttpStatus.OK).body(savedPage.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(1);
        }
    }

    // 새 문서 만들기
    public ResponseEntity<?> createNewDoc (String userId) {
        String modiUserId = userId.substring(1, userId.length() - 1);

        Page newPage = new Page();
        newPage.setOwner(modiUserId);
        newPage.setTitle("이름 없는 새 문서");
        newPage.setRDate(LocalDateTime.now());
        Page savePage = pageRepository.save(newPage);

        MyDoc newMyDoc = new MyDoc();
        newMyDoc.setStfNo(modiUserId);
        newMyDoc.setPno(savePage.getPno());
        MyDoc saveMyDoc = myDocRepository.save(newMyDoc);
        return ResponseEntity.status(HttpStatus.OK).body(saveMyDoc);
    }

    // 문서 파일 저장
    @Value("${file.upload.path}")
    private String fileUploadPath;
    public ResponseEntity<?> insertDocFile (MultipartFile file, int pno) {

        String path = new File(fileUploadPath).getAbsolutePath() + "/docImages/" + pno;

        String oName = file.getOriginalFilename();
        String ext = oName.substring(oName.lastIndexOf("."));
        String sName = "doc" + pno + "_" + UUID.randomUUID().toString()+ext;
        try {
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            file.transferTo(new File(path, sName));
        }catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File upload error");
        }

        String imageUrl = "/docImages/" + pno + "/" + sName;
        return ResponseEntity.status(HttpStatus.OK).body(imageUrl);
    }

    // 현재 문서의 공동 작업자 목록 조회
    public ResponseEntity<?> selectDocMember(int pno) {

        List<MyDoc> myDocList = myDocRepository.findByPno(pno);
        log.info("myDocList : " + myDocList);
        List<Map<String, String>> docMember = new ArrayList<>();
        for (MyDoc each : myDocList) {
            Map<String, String> result = new HashMap<>();
            result.put("stfNo", each.getStfNo());
            docMember.add(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(docMember);
    }

    // 현재 문서의 공동 작업자 초대
    public void insertDocMember(Map<String, Object> requestData) {
        log.info("requestData : " + requestData);

        MyDoc myDoc = new MyDoc();
        myDoc.setPno((int) requestData.get("pno"));
        myDoc.setStfNo((String) requestData.get("stfNo"));
        myDocRepository.save(myDoc);
    }

    // 현재 문서 삭제
    @Transactional
    public ResponseEntity<?> deleteDoc(int pno) {

        // 1.myDoc 삭제
        myDocRepository.deleteByPno(pno);

        // 2.이미지 삭제
        String path = new File(fileUploadPath).getAbsolutePath() + "/docImages/" + pno;
        File folder = new File(path);
        try {
            while(folder.exists()) {
                File[] fileList = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < fileList.length; i++) {
                    fileList[i].delete(); //파일 삭제
                }

                if(fileList.length == 0 && folder.isDirectory()){
                    folder.delete(); //대상폴더 삭제
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }

        // 3.page 삭제
        pageRepository.deleteById(pno);

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    // 현재 문서 나가기
    public ResponseEntity<?> exitDoc(Map<String, Object> requestData) {

        Optional<MyDoc> optMyDoc = myDocRepository.findByStfNoAndPno((String)requestData.get("userId"), (Integer)requestData.get("pno"));
        if (optMyDoc.isPresent()) {
            myDocRepository.deleteById(optMyDoc.get().getMyDocNo());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
}
