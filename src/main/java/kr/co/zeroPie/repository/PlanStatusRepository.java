package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.PlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanStatusRepository extends JpaRepository<PlanStatus,Integer> {
}
