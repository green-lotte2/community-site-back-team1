package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.QStf;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.custom.StfRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class StfRepositoryImpl implements StfRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QStf qStf = QStf.stf;

    public Page<Stf> searchUserTypeAndKeyword(PageRequestDTO pageRequestDTO, Pageable pageable){
        String keyword = pageRequestDTO.getKeyword();
        String type = pageRequestDTO.getType();

        BooleanExpression expression = null;
        List<BooleanExpression> expressions = new ArrayList<>();

        String[] types = null;
        if (type!=null){
            types = type.split(",");
        }

        if (types!=null){
            for(String t : types){
                try {
                    if ("stfStatus".equals(t)) {
                        expressions.add(qStf.stfStatus.contains(keyword));
                    }
                    if ("rnkNo".equals(t)) {
                        expressions.add(qStf.rnkNo.eq(Integer.valueOf(keyword)));
                    }
                    if ("dptNo".equals(t)) {
                        expressions.add(qStf.dptNo.eq(Integer.valueOf(keyword)));
                    }

                }catch (Exception e){
                    expressions.add(qStf.stfName.contains(keyword));
                }
            }
            if (!expressions.isEmpty()){
                expression = expressions.stream().reduce(BooleanExpression::and).orElse(null);
            }

        }else {
            expression = qStf.stfName.contains(keyword);
        }

        QueryResults<Stf> results = jpaQueryFactory
                .select(qStf)
                .from(qStf)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStf.stfEnt.desc())
                .fetchResults();

        List<Stf> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
