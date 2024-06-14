package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.MyDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MyDocRepository extends JpaRepository<MyDoc,Integer> {

}
