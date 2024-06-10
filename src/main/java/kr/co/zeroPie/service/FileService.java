package kr.co.zeroPie.service;

import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.ArticleDTO;
import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.entity.File;
import kr.co.zeroPie.repository.ArticleRepository;
import kr.co.zeroPie.repository.FileRepository;
import kr.co.zeroPie.util.CustomFileUtil;
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


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ArticleRepository articleRepository;
    private final CustomFileUtil customFileUtil;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public void saveFiles(FileDTO fileDTO) {
        List<MultipartFile> multiFileNames = fileDTO.getMultiFileNames();
        Map<String, String> savedFiles= customFileUtil.saveFiles(multiFileNames);

        int articleNo = fileDTO.getArticleNo();
        int fileCount = fileRepository.countByArticleNo(articleNo);

        for (Map.Entry<String, String> entry : savedFiles.entrySet()) {
            File file = File.builder()
                    .fileOname(entry.getKey())
                    .fileSname(entry.getValue())
                    .articleNo(articleNo)
                    .build();
            log.info("save file: {}", file);
            fileRepository.save(file);
        }

        int updatedRows  = articleRepository.updateFileCount(fileCount+ savedFiles.size(), articleNo);
        log.info("fileCount! : {}", updatedRows );
    }



    @Transactional
    public ResponseEntity<?> fileDownload(int fno)  {

        // 파일 조회
        File file = fileRepository.findById(fno).get();

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

    public void fileDelete(int articleNo) {
        List<File> files = fileRepository.findByArticleNo(articleNo);
        List<String> fileNames = files.stream().map(File::getFileSname).collect(Collectors.toList());
        fileRepository.deleteByArticleNo(articleNo);
        customFileUtil.deleteFiles(fileNames);
    }
/*
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