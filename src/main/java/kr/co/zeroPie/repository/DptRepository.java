package kr.co.zeroPie.repository;


import kr.co.zeroPie.entity.Dpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DptRepository extends JpaRepository<Dpt,Integer> {
}
