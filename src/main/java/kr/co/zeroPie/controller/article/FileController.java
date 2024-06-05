package kr.co.zeroPie.controller.article;

import kr.co.zeroPie.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;


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
