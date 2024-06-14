package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface PageRepositoryCustom {

    public List<Tuple> selectPageListById(String userId);

}
