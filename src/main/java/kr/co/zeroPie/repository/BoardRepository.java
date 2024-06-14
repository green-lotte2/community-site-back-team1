package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.Board;
import kr.co.zeroPie.entity.kanban.BoardOverview;
import kr.co.zeroPie.entity.kanban.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {

    List<Board> findByKanbanId(int kanbanId);


}
