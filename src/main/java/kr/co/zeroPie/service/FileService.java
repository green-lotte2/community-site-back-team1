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

    public void fileDeleteWithArticle(int articleNo) {
        List<File> files = fileRepository.findByArticleNo(articleNo);
        List<String> fileNames = files.stream().map(File::getFileSname).collect(Collectors.toList());
        fileRepository.deleteByArticleNo(articleNo);
        customFileUtil.deleteFiles(fileNames);
    }

    public void fileDelete(List<Integer> fileNos) {
        List<String> fileNames = new ArrayList<>();
        Integer articleNo = null;
        for (Integer fileNo : fileNos) {
            File file = fileRepository.findById(fileNo).orElse(null);
            if (file != null) {
                articleNo = file.getArticleNo();
                fileNames.add(file.getFileSname());
                fileRepository.deleteById(fileNo);
            }else {
                log.error("file not found : " + fileNo);
            }
        }
        if (!fileNames.isEmpty()) {
            customFileUtil.deleteFiles(fileNames);
        }

        if (articleNo != null) {
            // 이거말고 아티클에서 파일 갯수 찾는걸로 바꿔야함!!!!!!!!!!!!!!!!!!!!!!!!!!
            int fileCount = articleRepository.selectFileByArticleNo(articleNo);
            log.info("fileCount-article : "+ fileCount);
            int updatedRows  = articleRepository.updateFileCount(fileCount- fileNos.size(), articleNo);
            log.info("deleteFileCount : " + updatedRows);
        }

        log.info("Files deleted successfully for fileNos: " + fileNos);
    }

}