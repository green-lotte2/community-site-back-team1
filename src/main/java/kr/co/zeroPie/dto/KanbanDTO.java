package kr.co.zeroPie.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanDTO {

    private int kanbanId;//칸반 번호

    private String kanbanName;//칸반 이름
}
