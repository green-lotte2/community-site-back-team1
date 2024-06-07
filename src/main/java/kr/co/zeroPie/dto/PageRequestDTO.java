package kr.co.zeroPie.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PageRequestDTO {

    @Builder.Default
    private int no = 1;

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 10;

    private String csCate;
    private String stfNo;

    private String csReply;

    private String type;
    private String keyword;

    private String latest;
    private String hit;

    private String stfStatus;
    private int rnkNo;
    private int dptNo;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }

}
