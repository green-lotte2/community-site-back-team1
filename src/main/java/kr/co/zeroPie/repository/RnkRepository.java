package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Rnk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RnkRepository extends JpaRepository<Rnk,Integer> {
    List<Rnk> findAllByOrderByRnkIndexAsc();
}
