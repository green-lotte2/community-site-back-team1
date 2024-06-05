package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan,String> {
}
