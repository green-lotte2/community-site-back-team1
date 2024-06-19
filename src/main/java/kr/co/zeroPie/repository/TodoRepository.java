package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<ToDo, Integer> {

    public List<ToDo> findByStfNoAndTodoStatusOrderByTodoDateDesc(String stfNo, String todoStatus);

}
