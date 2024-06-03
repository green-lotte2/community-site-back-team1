package kr.co.zeroPie.dto;


import kr.co.zeroPie.entity.File;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO {

    private int fileNo;

    private String fileOname;//파일 원래 이름

    private String fileSname;//변경된 이름

    private int fileDownload;//파일 다운로드 횟수

    private int articleNo;//게시글 번호

    private LocalDateTime fileRdate;

    // 파일 경로 저장 필드 추가
    private String filePath;

    public File toEntity(){
        return File.builder()
                .fileNo(fileNo)
                .articleNo(articleNo)
                .fileOname(fileOname)
                .fileSname(fileSname)
                .fileDownload(fileDownload)
                .fileRdate(fileRdate)
                .filePath(filePath)
                .build();
    }
}
