package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.custom.StfRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StfRepository extends JpaRepository<Stf, String>,StfRepositoryCustom {
    public int countByStfEmail(String stfEmail);
    public int countByStfEmailAndStfName(String stfEmail,String name);
    public int countByStfEmailAndStfNo(String stfEmail,String id);


    public Stf findIdByStfEmailAndStfName(String stfEmail,String stfName);

    public Stf findStfNameByStfNo(String stfNo);

    // 생일자 조회
    public List<Stf> findByStfBirth(LocalDate today);

    // 전화번호 중복 검사
    public Optional<Stf> findByStfPh(String stfPh);

    Optional<Stf> findByStfEmail(String stfEmail);

    public Stf findByStfNo(String uid);

    long countByStfStatus(String stfStatus);
}
