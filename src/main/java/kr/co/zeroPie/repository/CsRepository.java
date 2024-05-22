package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.repository.custom.CsRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CsRepository extends JpaRepository<Cs,Integer>, CsRepositoryCustom {

}
