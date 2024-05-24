package kr.co.zeroPie.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ArticlePageRequestDTO {

    @Builder.Default                // @Builder.Default : 기본값 설정
    private int articleNo = 1;             // 페이지 요청의 번호

    @Builder.Default
    private int pg = 1;             // 요청된 페이지 번호

    @Builder.Default
    private int size = 10;          // 페이지당 항목 수(한 페이지에 표시할 수)

    private int articleCateNo;            // 카테고리

    // 검색창
    private String type;
    private String keyword;

    // 기간선택
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 정렬

    public Pageable getPageable(String sort){
        Sort.Direction direction;
        if ("ASC".equalsIgnoreCase(sort)) {
            direction = Sort.Direction.ASC;
        } else {
            // 기본값
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(this.pg - 1, this.size, Sort.by(direction, "createdAt"));
    }

}
