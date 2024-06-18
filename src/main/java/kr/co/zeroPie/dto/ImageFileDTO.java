package kr.co.zeroPie.dto;

import kr.co.zeroPie.entity.ImageFile;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageFileDTO {

    private Long id;
    private int articleNo;
    private String filePath;

    public ImageFile toEntity() {
        return ImageFile.builder()
                .id(id)
                .articleNo(articleNo)
                .filePath(filePath)
                .build();
    }
}
