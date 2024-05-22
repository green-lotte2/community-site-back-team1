package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Stf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StfRepository extends JpaRepository<Stf, String> {
}
