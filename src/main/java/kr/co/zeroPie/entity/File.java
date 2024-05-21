package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;
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

    private int fileDownload;//파일 다운로드 횟수

    private int articleNo;//게시글 번호

    @CreationTimestamp
    private LocalDateTime fileRdate;
}
