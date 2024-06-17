package kr.co.zeroPie.controller.kanban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.kanban.BoardDTO;
import kr.co.zeroPie.dto.kanban.KanbanDTO;
import kr.co.zeroPie.dto.kanban.KanbanStfDTO;
import kr.co.zeroPie.entity.kanban.Board;
import kr.co.zeroPie.service.kanban.KanbanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/kanban/{kanbanId}")
    public List<BoardDTO> getAllBoards(@PathVariable int kanbanId) throws JsonProcessingException {
        log.info("보드 겟");
        return kanbanService.getBoardById(kanbanId);
    }

    @GetMapping("/kanban/stfList/{kanbanId}")
    public List<KanbanStfDTO> getKanbanStfList(@PathVariable int kanbanId){
        return kanbanService.getKanbanStfList(kanbanId);
    }

    // 보드
    @PostMapping("/kanban/addBoard")
    public void addBoard(@RequestBody List<BoardDTO> boardDTOList) throws JsonProcessingException {
            log.info("인서트!!!!");
        kanbanService.saveBoard(boardDTOList);
    }

    @DeleteMapping("/kanban/{id}")
    public void deleteBoard(@PathVariable String id) {
        kanbanService.removeBoard(id);
    }

    @DeleteMapping("/kanban/del/{kanbanId}")
    public void delKanban(@PathVariable int kanbanId) {
        kanbanService.removeKanban(kanbanId);
    }

    @DeleteMapping("/kanban/stf")
    public ResponseEntity<String> deleteStf(@RequestParam("kanbanNo") int kanbanNo, @RequestParam("stfNo") String stfNo) {
        log.info("deleteStf"+stfNo);
        log.info("deleteStf"+kanbanNo);
        return kanbanService.removeStf(kanbanNo, stfNo);
    }
}
