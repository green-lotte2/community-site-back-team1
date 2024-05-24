package kr.co.zeroPie.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
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

    private String type;
    private String keyword;

    private LocalDate startDate;
    private LocalDate endDate;
    private long period;

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

    // startDate, endDate를 기반으로 period를 계산하는 메서드
    public void calculatePeriod() {
        // startDate와 endDate가 null이 아닌지 확인
        if (startDate != null && endDate != null) {
            // 두 날짜 사이의 일 수 계산
            period = ChronoUnit.DAYS.between(startDate, endDate);
        } else {
            // startDate 또는 endDate가 null인 경우 예외 처리
            throw new IllegalArgumentException("startDate 또는 endDate가 null입니다.");
        }
    }

    // 년도, 월, 일을 기반으로 startDate 설정
    public void setStartDate(int startYear, int startMonth, int startDay) {
        this.startDate = LocalDate.of(startYear, startMonth, startDay);
    }

    // 년도, 월, 일을 기반으로 endDate 설정
    public void setEndDate(int endYear, int endMonth, int endDay) {
        this.endDate = LocalDate.of(endYear, endMonth, endDay);
    }

}
