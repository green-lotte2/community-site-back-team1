package kr.co.zeroPie.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);

        if(!tempFolder.exists()) {
            boolean result = tempFolder.mkdir();
        }

        uploadPath = tempFolder.getAbsolutePath();

        log.info("-------------------------------------");
        log.info(uploadPath);
    }

    public Map<String, String> saveFiles(List<MultipartFile> files)throws RuntimeException{


        if(files == null || files.size() == 0){
            return null;
        }

        Map<String, String> uploadNames = new HashMap<>();

        for (MultipartFile multipartFile : files) {

            String fieldName = multipartFile.getName();
            String originalFilename = multipartFile.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString() + ext;

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(multipartFile.getInputStream(), savePath);
                uploadNames.put(originalFilename, savedName);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }//end for
        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath+ File.separator + fileName);

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }


    public void deleteFiles(List<String> fileNames) {

        if(fileNames == null || fileNames.size() == 0){
            return;
        }

        fileNames.forEach(fileName -> {

            Path thumbnailPath = Paths.get(uploadPath, fileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    // 이미지 업로드 메서드
    public String fileUpload(MultipartFile images, String thumbnailSize){
        String path = new File(uploadPath).getAbsolutePath();

        if (!images.isEmpty()){
            String oName = images.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString()+ ext;

            try {
                // 이미지를 메모리에 로드하여 썸네일 생성 후 저장하지 않고 경로 반환
                String thumbnailPath = mkThumbnail(path, sName, images, thumbnailSize);

                return thumbnailPath; // 썸네일 이미지 경로 반환
            } catch (IOException e) {
                log.error("Failed to create thumbnail: " + e.getMessage());
                return null;
            }
        }

        return null;
    }

    //썸네일 이미지 크기 조절 메서드
    private String mkThumbnail(String path, String sName, MultipartFile images, String thumbnailSize) throws IOException {
        switch (thumbnailSize) {
            case "thumb190":
                Thumbnails.of(images.getInputStream())
                        .size(190, 190)
                        .toFile(new File(path, "thumb190_" + sName));
                return "thumb190_" + sName;
            case "thumb230":
                Thumbnails.of(images.getInputStream())
                        .size(230, 230)
                        .toFile(new File(path, "thumb230_" + sName));
                return "thumb230_" + sName;
            case "thumb456":
                Thumbnails.of(images.getInputStream())
                        .size(456, 456)
                        .toFile(new File(path, "thumb456_" + sName));
                return "thumb456_" + sName;
            case "thumb940":
                Thumbnails.of(images.getInputStream())
                        .width(940)
                        .toFile(new File(path, "thumb940_" + sName));
                return "thumb940_" + sName;
            default:
                return null;
        }
    }
}
