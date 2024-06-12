package kr.co.zeroPie.controller.kanban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.kanban.BoardDTO;
import kr.co.zeroPie.dto.kanban.KanbanDTO;
import kr.co.zeroPie.dto.kanban.KanbanStfDTO;
import kr.co.zeroPie.service.kanban.KanbanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
public class KanbanController {

    private final KanbanService kanbanService;

    @GetMapping("/kanban/list")
    public List<KanbanDTO> kanbanList(@RequestParam("kanbanStf") String kanbanStf){
        log.info("kanbanStf: " + kanbanStf);

        return kanbanService.getKanbanList(kanbanStf);
    }

    @PostMapping("/kanban/creat")
    public int createKanban(@RequestBody KanbanDTO kanbanDTO) {
        log.info("Create Kanban : " + kanbanDTO.toString());

        return kanbanService.createKanban(kanbanDTO);
    }

    @PostMapping("/kanban/member")
    public void createKanbanMember(@RequestBody KanbanStfDTO kanbanStfDTO) {
        log.info("Create KanbanMember : " + kanbanStfDTO.toString());

        kanbanService.createMember(kanbanStfDTO);
    }

    // 보드
    @PostMapping("/kanban/addBoard")
    public void addBoard(@RequestBody String boardJson) {
        kanbanService.createBoard(boardJson);

    }
}
