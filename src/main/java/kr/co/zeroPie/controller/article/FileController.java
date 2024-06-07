package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.dto.FileDTO;
import kr.co.zeroPie.service.FileService;
import kr.co.zeroPie.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

        List<MultipartFile> multiFileNames = fileDTO.getMultiFileNames();

        Map<String, String> savedFiles= customFileUtil.saveFiles(multiFileNames);
        log.info("Saved files : {}", savedFiles);
    }

    /*
    @GetMapping("/file/download/{fileNo}")
    public ResponseEntity<?> fileDownload(@PathVariable("fileNo") int fileNo) {
        log.info("fileDownload : " + fileNo);
        return fileService.fileDownload(fileNo);
    }

    @GetMapping("/file/downloadCount/{fileNo}")
    public ResponseEntity<?> fileDownloadCount(@PathVariable("fileNo") int fileNo) {
        log.info("fileDownloadCount : " + fileNo);
        return fileService.fileDownloadCount(fileNo);
    }

     */


}
