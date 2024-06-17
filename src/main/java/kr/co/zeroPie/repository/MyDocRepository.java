package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.MyDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MyDocRepository extends JpaRepository<MyDoc,Integer> {
    // 현재 문서 공동 작업자 조회
    public List<MyDoc> findByPno(int pno);

    // 현재 문서 삭제
    public void deleteByPno(int pno);
}
