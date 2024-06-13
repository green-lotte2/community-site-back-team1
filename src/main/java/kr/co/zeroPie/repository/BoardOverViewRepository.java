package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.BoardOverview;
import kr.co.zeroPie.entity.kanban.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardOverViewRepository extends JpaRepository<BoardOverview, String> {

    @Query("SELECT b FROM BoardOverview b WHERE b.kanbanId = :kanbanId")
    List<BoardOverview> findByKanbanId(int kanbanId);

}
