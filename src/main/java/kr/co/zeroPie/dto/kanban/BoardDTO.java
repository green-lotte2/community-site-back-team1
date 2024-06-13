package kr.co.zeroPie.dto.kanban;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private String id;
    private String boardName;
    private int kanbanId;

    private List<CardDTO> card;
}
