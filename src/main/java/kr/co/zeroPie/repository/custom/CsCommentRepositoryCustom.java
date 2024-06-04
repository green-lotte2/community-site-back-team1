package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.entity.CsComment;

import java.util.List;

public interface CsCommentRepositoryCustom {

    public List<Tuple> findByCsNo(int csNo);
}
