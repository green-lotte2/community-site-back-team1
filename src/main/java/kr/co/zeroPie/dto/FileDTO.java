package kr.co.zeroPie.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.zeroPie.entity.File;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {

    private int fileNo;

    private String fileOname;//파일 원래 이름
    @JsonIgnore
    private List<MultipartFile> multiFileNames; // 저장위해 가져옴

    private String fileSname;//변경된 이름

    private int fileDownload;//파일 다운로드 횟수

    private int articleNo;//게시글 번호

    private LocalDateTime fileRdate;


    public File toEntity(){
        return File.builder()
                .fileNo(fileNo)
                .fileOname(fileOname)
                .fileSname(fileSname)
                .fileDownload(fileDownload)
                .articleNo(articleNo)
                .fileRdate(fileRdate)
                .build();
    }
}
