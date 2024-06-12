package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {



}
