package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {



}
