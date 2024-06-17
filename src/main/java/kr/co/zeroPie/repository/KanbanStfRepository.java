package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.KanbanStf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanbanStfRepository extends JpaRepository<KanbanStf, Integer> {

    List<KanbanStf> findByStfNo(String stfNo);
    KanbanStf findByKanbanIdAndStfNo(int kanbanId, String stfNo);
    List<KanbanStf> findByKanbanId(int kanbanId);

}
