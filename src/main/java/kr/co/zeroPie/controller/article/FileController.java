package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.service.FileService;
import kr.co.zeroPie.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;
    private final CustomFileUtil customFileUtil;


    @PostMapping("/article/file/upload")
    public void uploadFile(FileDTO fileDTO) {
        log.info("Upload file : {}", fileDTO );

        fileService.saveFiles(fileDTO);

    }
    @GetMapping("/article/file/download")
    public ResponseEntity<?> fileDownload(@RequestParam("fileNo") int fileNo) {
        log.info("fileDownload : " + fileNo);
        return fileService.fileDownload(fileNo);
    }

    @DeleteMapping("/article/file/delete")
    public void deleteFile(@RequestParam("fileNo") List<Integer>fileNos) {
        log.info("deleteFile : " + fileNos);
        fileService.fileDelete(fileNos);
    }


}
