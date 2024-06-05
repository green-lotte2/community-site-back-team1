package kr.co.zeroPie.service;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public int fileUpload(ArticleDTO articleDTO){
        if (fileUploadPath.startsWith("file:")) {
            fileUploadPath =  fileUploadPath.substring("file:".length());
        };

        String path = new File(fileUploadPath).getAbsolutePath();  //실제 업로드 할 시스템상의 경로 구하기
        int articleNo = articleDTO.getArticleNo();
        int count = 0;

        // 원본 파일 폴더 자동 생성
        String orgPath = path + "/orgArtImage";
        File orgFile = new File(orgPath);
        if (!orgFile.exists()) {
            orgFile.mkdir();
        }

        for(MultipartFile mf : articleDTO.getFiles()){
            if(mf.getOriginalFilename() !=null && mf.getOriginalFilename() != ""){
                // oName, sName 구하기
                String fileOname = mf.getOriginalFilename();
                String ext = fileOname.substring(fileOname.lastIndexOf(".")); //확장자
                String fileSname = UUID.randomUUID().toString()+ ext;

                log.info("fileOname : "+fileOname);

                // 파일 업로드
                try{
                    //upload directory에 upload가 됨 - 원본파일 저장
                    mf.transferTo(new File(orgPath, fileSname));

                    FileDTO fileDTO = FileDTO.builder()
                            .articleNo(articleNo)
                            .fileOname(fileOname)
                            .fileSname(fileSname)
                            .build();
                    fileRepository.save(fileDTO.toEntity());
                    count++;
                }catch (IOException e){
                    log.error("fileUpload : "+e.getMessage());
                }
            }
        }
        return count;
    }

/*
    @Transactional
    public ResponseEntity<?> fileDownload(int fno)  {

        // 파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();

        try {
            Path path = Paths.get(fileUploadPath + file.getSName());
            String contentType = Files.probeContentType(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(file.getOName(), StandardCharsets.UTF_8).build());

            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));

            // 파일 다운로드 카운트 업데이트
            file.setDownload(file.getDownload() + 1);
            fileRepository.save(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        }catch (IOException e){
            log.error("fileDownload : " + e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fileDownloadCount(int fno)  {

        // 파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();

        // 다운로드 카운트 Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", file.getDownload());

        return ResponseEntity.ok().body(resultMap);
    }
}

    // 여러파일삭제(게시글 삭제)
    public void deleteFiles(int bno){

        if (fileUploadPath.startsWith("file:")) {
            fileUploadPath =  fileUploadPath.substring("file:".length());
        };

        String path = new File(fileUploadPath).getAbsolutePath();
        List<kr.co.zeroPie.entity.File> files = fileRepository.findFilesByBno(bno);
        for(kr.co.zeroPie.entity.File file : files){
            String sfile = file.getSfile();
            int fno = file.getFileNo();
            fileRepository.deleteById(fno);

            File deleteFile = new File(fileUploadPath+File.separator+sfile);
            if(deleteFile.exists()){
                deleteFile.delete();
            }
        }
    }

    @Transactional
    public void deleteFileBysName(String sfile){

        if (fileUploadPath.startsWith("file:")) {
            fileUploadPath =  fileUploadPath.substring("file:".length());
        };

        // 해당 sFile의 파일이 있는지 확인
        BoardFileEntity fileEntity = fileRepository.findBySfile(sfile);

        // 있다면 삭제
        if (fileEntity != null){
            fileRepository.deleteBySfile(fileEntity.getSfile());
        }
    }

 */
}