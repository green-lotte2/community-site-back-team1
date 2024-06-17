package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.custom.StfRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StfRepository extends JpaRepository<Stf, String>,StfRepositoryCustom {
    public int countByStfEmail(String stfEmail);

    public Stf findIdByStfEmailAndStfName(String stfEmail,String stfName);

    // 생일자 조회
    public List<Stf> findByStfBirth(LocalDate today);
}
