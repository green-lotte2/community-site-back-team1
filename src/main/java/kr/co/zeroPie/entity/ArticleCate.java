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

    private int articleCateStatus; // 카테고리 상태

    private String articleCateRole; // 카테고리 권한

    private String articleCateCoRole; // 카테고리 댓글 권한
}
