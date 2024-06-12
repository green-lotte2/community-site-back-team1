package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KanbanRepository extends JpaRepository<Kanban, Integer> {

    List<Kanban> findByKanbanStf(String kanbanStf);

}
