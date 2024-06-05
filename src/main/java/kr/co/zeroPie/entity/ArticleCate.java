package kr.co.zeroPie.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "articlecate")
public class ArticleCate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleCateNo;//카테고리 번호

    private String articleCateName;//카테고리 이름
    private String articleCateOutput; //카테고리 표시 형태(카드, 리스트)
    private int articleCateStatus; // 카테고리 상태

    private String articleCateVRole; // 카테고리 보기 권한
    private String articleCateWRole; // 카테고리 쓰기 권한

    private String articleCateCoRole; // 카테고리 댓글 권한


}
