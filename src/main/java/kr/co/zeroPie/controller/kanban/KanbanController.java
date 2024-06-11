package kr.co.zeroPie.controller.kanban;

import kr.co.zeroPie.service.kanban.KanbanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Log4j2
public class KanbanController {

    private final KanbanService kanbanService;
}
