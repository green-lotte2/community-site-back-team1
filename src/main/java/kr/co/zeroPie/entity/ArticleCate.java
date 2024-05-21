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
}
