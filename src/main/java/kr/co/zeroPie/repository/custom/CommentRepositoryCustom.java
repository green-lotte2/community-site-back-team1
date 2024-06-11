package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface CommentRepositoryCustom {

    public List<Tuple> findByArticleNo(int articleNo);
}
