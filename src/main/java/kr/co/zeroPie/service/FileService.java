package kr.co.zeroPie.service;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;

/*
    public List<FileDTO> fileUpload(ArticleDTO articleDTO){

        // 파일 업로드 시스템 경로 구하기
        String path = new File(fileUploadPath).getAbsolutePath();

        // 파일 정보 리턴을 위한 리스트
        List<FileDTO> files = new ArrayList<>();

        // 첨부한 파일 갯수만큼 반복 처리
        for(MultipartFile mf : articleDTO.getFiles()){

            //파일 첨부 안하면 에러나기 때문에 if문으로 isEmpty()로 첨부여부 먼저 확인
            if(!mf.isEmpty()) {

                String fileOname = mf.getOriginalFilename();
                String ext = fileOname.substring(fileOname.lastIndexOf("."));
                String fileSname = UUID.randomUUID().toString() + ext;

                try {
                    // 저장
                    mf.transferTo(new File(path, fileSname));

                    // 파일 정보 생성
                    FileDTO fileDTO = FileDTO.builder()
                            .fileOname(fileOname)
                            .fileSname(fileSname)
                            .build();

                    // 리스트 저장
                    files.add(fileDTO);

                } catch (IOException e) {
                    log.error("fileUpload : " + e.getMessage());
                }
            }
        }
        // 저장한 파일 정보 리스트 반환
        return files;
    }


    @Transactional
    public ResponseEntity<?> fileDownload(int fno)  {

        // 파일 조회
        kr.co.zeroPie.entity.File file = fileRepository.findById(fno).get();


        try {
            Path path = Paths.get(fileUploadPath + file.getFileSname());
            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(file.getFileOname(), StandardCharsets.UTF_8).build());

            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            // 파일 다운로드 카운트 업데이트
            file.setFileDownload(file.getFileDownload() + 1);
            fileRepository.save(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        }catch (IOException e){
            log.error("fileDownload : " + e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fileDownloadCount(int fno)  {

        // 파일 조회
        kr.co.zeroPie.entity.File file = fileRepository.findById(fno).get();

        // 다운로드 카운트 Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", file.getFileDownload());

        return ResponseEntity.ok().body(resultMap);
    }
*/
}
