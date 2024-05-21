package kr.co.zeroPie.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCateDTO {

    private int articleCateNo;//카테고리 번호

    private String articleCateName;//카테고리 이름
}
