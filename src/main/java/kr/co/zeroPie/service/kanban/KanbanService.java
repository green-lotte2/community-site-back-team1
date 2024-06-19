package kr.co.zeroPie.service.kanban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.zeroPie.dto.kanban.BoardDTO;
import kr.co.zeroPie.dto.kanban.KanbanDTO;
import kr.co.zeroPie.dto.kanban.KanbanStfDTO;
import kr.co.zeroPie.entity.kanban.Board;
import kr.co.zeroPie.entity.kanban.Kanban;
import kr.co.zeroPie.entity.kanban.KanbanStf;
import kr.co.zeroPie.repository.BoardOverViewRepository;
import kr.co.zeroPie.repository.BoardRepository;
import kr.co.zeroPie.repository.KanbanRepository;
import kr.co.zeroPie.repository.KanbanStfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class KanbanService {

    private final KanbanRepository kanbanRepository;
    private final KanbanStfRepository kanbanStfRepository;
    private final BoardRepository boardRepository;
    private final BoardOverViewRepository boardOverViewRepository;
    private final ObjectMapper objectMapper;


    public List<KanbanDTO> getKanbanList (String kanbanStf){

        List<KanbanStf> stfList = kanbanStfRepository.findByStfNo(kanbanStf);

        List<KanbanDTO> kanbanDTOList = new ArrayList<>();
        for (KanbanStf stf : stfList) {
            int kanbanId = stf.getKanbanId();
            Optional<Kanban> findKanban = kanbanRepository.findById(kanbanId);
            if (findKanban.isPresent()) {
                Kanban foundKanban = findKanban.get();
                KanbanDTO kanbanDTO = new KanbanDTO();
                kanbanDTO.setKanbanId(foundKanban.getKanbanId());
                kanbanDTO.setKanbanName(foundKanban.getKanbanName());
                kanbanDTO.setKanbanInfo(foundKanban.getKanbanInfo());
                kanbanDTO.setKanbanStf(foundKanban.getKanbanStf());
                kanbanDTOList.add(kanbanDTO);
            }
        }

        log.info("getKanbanList: " + kanbanDTOList);

        return kanbanDTOList;
    }

    public int createKanban(KanbanDTO kanbanDTO){
        Kanban kanban = new Kanban();
        kanban.setKanbanName(kanbanDTO.getKanbanName());
        kanban.setKanbanInfo(kanbanDTO.getKanbanInfo());
        kanban.setKanbanStf(kanbanDTO.getKanbanStf());

        Kanban savedKanban = kanbanRepository.save(kanban);

        return savedKanban.getKanbanId();
    }

    public void createMember(KanbanStfDTO kanbanStfDTO){
        KanbanStf kanbanStf = new KanbanStf();
        kanbanStf.setKanbanId(kanbanStfDTO.getKanbanId());
        kanbanStf.setStfNo(kanbanStfDTO.getStfNo());

        kanbanStfRepository.save(kanbanStf);
    }

    public List<KanbanStfDTO> getKanbanStfList(int kanbanId){
        List<KanbanStf> stfList= kanbanStfRepository.findByKanbanId(kanbanId);
        log.info("스태프찾기 : "+stfList.toString());

        return stfList.stream()
                .map(stf -> {
                    KanbanStfDTO dto = new KanbanStfDTO();
                    dto.setKanbanStfNo(stf.getKanbanStfNo());
                    dto.setKanbanId(stf.getKanbanId());
                    dto.setStfNo(stf.getStfNo());
                    return dto;
                })
                .toList();
    }

    public List<Board> saveBoard(List<BoardDTO> boardDTOList) throws JsonProcessingException {
        List<Board> savedBoards = new ArrayList<>();

        int index = 0;
        for (BoardDTO boardDTO : boardDTOList) {
            //log.info("인서트 폴문안");
            log.info("boardDTO: " + boardDTO.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            // Convert BoardDTO to Board
            Board board = Board.builder()
                    .id(boardDTO.getId())
                    .boardName(boardDTO.getBoardName())
                    .kanbanId(boardDTO.getKanbanId())
                    .card(objectMapper.writeValueAsString(boardDTO.getCard()))
                    .boardIndex(index++) // Set the index and then increment it
                    .build();
            log.info("서비스" + board.toString());
            savedBoards.add(boardRepository.save(board));
        }
        return savedBoards;
    }

    public ResponseEntity<String> removeBoard(String boardId){
        try{
            if (boardRepository.existsById(boardId)) {
                boardRepository.deleteById(boardId);
                return ResponseEntity.ok("removed Board");
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> removeKanban(int kanbanId){
        try{
            if (kanbanRepository.existsById(kanbanId)) {
                kanbanRepository.deleteById(kanbanId);
                return ResponseEntity.ok("removed Kanban");
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> removeStf(int kanbanId, String stfNo){
        KanbanStf kanbanStf = kanbanStfRepository.findByKanbanIdAndStfNo(kanbanId, stfNo);
        log.info(kanbanStf.toString());
        if (kanbanStf != null) {
            int kanbanStfNo = kanbanStf.getKanbanStfNo();
            kanbanStfRepository.deleteById(kanbanStfNo);
            return ResponseEntity.ok("removed stf");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<BoardDTO> getBoardById(int kanbanId) throws JsonProcessingException {
        List<BoardDTO> boardDTOs = null;
        List<Board> boardList = boardRepository.findByKanbanId(kanbanId);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            boardDTOs = new ArrayList<>();
            for (Board board : boardList) {
                BoardDTO boardDTO = new BoardDTO();
                boardDTO.setId(board.getId());
                boardDTO.setBoardName(board.getBoardName());
                boardDTO.setKanbanId(board.getKanbanId());
                boardDTO.setBoardIndex(board.getBoardIndex());

                if (board.getCard() == null || board.getCard().isEmpty()) {
                    boardDTO.setCard(Collections.emptyList());
                } else {
                    List<Map<String, Object>> cardList = objectMapper.readValue(board.getCard(), new TypeReference<List<Map<String, Object>>>() {});
                    boardDTO.setCard(cardList);
                }

                boardDTOs.add(boardDTO);
            }
            boardDTOs.sort(Comparator.comparingInt(BoardDTO::getBoardIndex));

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("보드 조회"+boardDTOs.toString());
        return boardDTOs != null ? boardDTOs : Collections.emptyList();
    }


}
