package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.repository.custom.CsRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CsRepository extends JpaRepository<Cs,Integer>, CsRepositoryCustom {

    // 최근 2일간의 신규 문의 수
    int countByCsRdateAfter(LocalDateTime dateTime);
    // 답변 대기 중인 글 수
    int countByCsReply(int csReply);
}
