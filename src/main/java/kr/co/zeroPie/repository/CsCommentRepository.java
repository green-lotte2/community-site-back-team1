package kr.co.zeroPie.repository;

import kr.co.zeroPie.entity.CsComment;
import kr.co.zeroPie.repository.custom.CsCommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsCommentRepository extends JpaRepository<CsComment,Integer>,CsCommentRepositoryCustom {

    public void deleteByCsNo(int csNo);

}
