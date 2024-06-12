package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.kanban.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {



}
