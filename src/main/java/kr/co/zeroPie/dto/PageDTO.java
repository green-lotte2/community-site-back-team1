package kr.co.zeroPie.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {
    private int pno;
    private String title;
    private String owner;
    private LocalDateTime rDate;
    private String document;
}
