package kr.co.zeroPie.entity;

import jakarta.persistence.*;
import kr.co.zeroPie.dto.ImageFileDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "imageFile")
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "articleNo", nullable = false)
    private int articleNo;

    @Column(name = "filePath", nullable = false)
    private String filePath;

    public ImageFileDTO toDTO() {
        return ImageFileDTO.builder()
                .id(id)
                .articleNo(articleNo)
                .filePath(filePath)
                .build();
    }
}
