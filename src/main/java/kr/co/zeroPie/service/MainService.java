package kr.co.zeroPie.service;

import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.dto.ToDoDTO;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.entity.ToDo;
import kr.co.zeroPie.repository.StfRepository;
import kr.co.zeroPie.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MainService {

    private final StfRepository stfRepository;
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final StfService stfService;

    // 마이페이지 연락처 변경
    public ResponseEntity<?> stfPhSave(String stfPh, String stfNo) {
        Optional<Stf> optStf = stfRepository.findById(stfNo);

        if(optStf.isPresent()) {
            optStf.get().setStfPh(stfPh);
            stfRepository.save(optStf.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 마이페이지 이메일 변경
    public ResponseEntity<?> stfEmailSave(String stfEmail, String stfNo) {
        Optional<Stf> optStf = stfRepository.findById(stfNo);

        if(optStf.isPresent()) {
            optStf.get().setStfEmail(stfEmail);
            stfRepository.save(optStf.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 마이페이지 주소 변경
    public ResponseEntity<?> stfAddrSave(Map<String, Object> requestMap) {
        Optional<Stf> optStf = stfRepository.findById((String) requestMap.get("stfNo"));

        if(optStf.isPresent()) {
            optStf.get().setStfZip(Integer.parseInt((String) requestMap.get("stfZip")));
            optStf.get().setStfAddr1((String)requestMap.get("stfAddr1"));
            optStf.get().setStfAddr2((String)requestMap.get("stfAddr2"));
            stfRepository.save(optStf.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 마이페이지 프로필 변경
    @Value("${file.upload.path}")
    private String fileUploadPath;
    public ResponseEntity<?> stfProfileSave(MultipartFile file, String stfNo) {

        StfDTO stfDTO = stfService.uploadReviewImage(file);

        Optional<Stf> optStf = stfRepository.findById(stfNo);

        if(optStf.isPresent()) {
            String oldFileName = optStf.get().getStfImg();
            // 여기
            optStf.get().setStfImg(stfDTO.getSName());
            stfRepository.save(optStf.get());

            if (oldFileName != null && !oldFileName.isEmpty()) {
                try {
                    File oldFile = new File(fileUploadPath, oldFileName);
                    if (oldFile.exists()) {
                        if (!oldFile.delete()) {
                            log.info("삭제실패");
                        }
                    }
                } catch (Exception e) {
                    // 파일 삭제 중 에러 발생 시 로그 출력
                    e.printStackTrace();
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // todo 완료
    public ResponseEntity<?> todoComplete(int todoNo) {
        Optional<ToDo> optTodo = todoRepository.findById(todoNo);

        if (optTodo.isPresent()) {
            optTodo.get().setTodoStatus("N");
            todoRepository.save(optTodo.get());
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // todo 생성
    public ResponseEntity<?> createTodo(ToDoDTO toDoDTO) {

        todoRepository.save(modelMapper.map(toDoDTO, ToDo.class));
        return ResponseEntity.status(HttpStatus.OK).body(1);
    }
}
