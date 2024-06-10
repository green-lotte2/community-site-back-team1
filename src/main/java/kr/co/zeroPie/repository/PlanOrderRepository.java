package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.PlanOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanOrderRepository extends JpaRepository<PlanOrder,Integer> {
}
