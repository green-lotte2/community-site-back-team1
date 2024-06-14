package kr.co.zeroPie.dto.kanban;

import lombok.*;

import java.util.List;
import java.util.Map;

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
    private int boardIndex;

    private List<Map<String, Object>> card;
}
