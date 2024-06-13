package kr.co.zeroPie.dto.kanban;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO {
    private String id;
    private String title;
    //private String board_id;

    private List<TagDTO> tags;
    private List<TaskDTO> task;
}
