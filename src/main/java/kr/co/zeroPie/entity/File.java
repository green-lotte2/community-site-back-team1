package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import kr.co.zeroPie.dto.FileDTO;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;

    private String fileOname;//파일 원래 이름

    private String fileSname;//변경된 이름

    @ColumnDefault("0")
    private int fileDownload;//파일 다운로드 횟수

    private int articleNo;//게시글 번호

    @CreationTimestamp
    private LocalDateTime fileRdate;

    // 파일 경로 저장 필드 추가
    // private String filePath;

    public FileDTO toDTO(){
        return FileDTO.builder()
                .fileNo(fileNo)
                .fileOname(fileOname)
                .fileSname(fileSname)
                .fileDownload(fileDownload)
                .articleNo(articleNo)
                .fileRdate(fileRdate)
                .build();
    }
}
