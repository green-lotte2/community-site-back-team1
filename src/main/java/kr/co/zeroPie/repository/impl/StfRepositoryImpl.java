package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.QDpt;
import kr.co.zeroPie.entity.QRnk;
import kr.co.zeroPie.entity.QStf;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.custom.StfRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class StfRepositoryImpl implements StfRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QStf qStf = QStf.stf;
    private final QRnk qRnk = QRnk.rnk;
    private final QDpt qDpt = QDpt.dpt;

    // 사원 검색
    public Page<Stf> searchUserTypeAndKeyword(PageRequestDTO pageRequestDTO, Pageable pageable){

        QueryResults<Stf> results = jpaQueryFactory
                .selectFrom(qStf)
                .where(
                    stfStatusEq(pageRequestDTO.getStfStatus()),
                        rnkNoEq(pageRequestDTO.getRnkNo()),
                        dptNoEq(pageRequestDTO.getDptNo()),
                        stfEntBetween(pageRequestDTO.getStartDate(), pageRequestDTO.getEndDate()),
                        keywordContains(pageRequestDTO.getType(), pageRequestDTO.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStf.stfEnt.desc())
                .fetchResults();

        List<Stf> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    // 사원 상태
    private BooleanExpression stfStatusEq(String stfStatus){
        return stfStatus != null ? qStf.stfStatus.eq(stfStatus) : null;
    }

    private BooleanExpression rnkNoEq(int rnkNo){
        return rnkNo != 0 ? qStf.rnkNo.eq(rnkNo) : null;
    }

    private BooleanExpression dptNoEq(int dptNo) {
        return dptNo != 0 ? qStf.dptNo.eq(dptNo) : null;
    }

    private BooleanExpression stfEntBetween(LocalDate startDate, LocalDate endDate){
        if(startDate != null && endDate != null){
            return qStf.stfEnt.between(startDate, endDate);
        }else if (startDate != null){
            return qStf.stfEnt.goe(startDate);
        }else if (endDate != null){
            return qStf.stfEnt.loe(endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression keywordContains(String type, String keyword){
        if (keyword != null && !keyword.isEmpty()) {
            if ("stfName".equals(type)) {
                return qStf.stfName.contains(keyword);
            } else if ("stfEmail".equals(type)) {
                return qStf.stfEmail.contains(keyword);
            }
        }
        return null;
    }

    // 사원 + 직급이름
    public List<Tuple> stfRank() {
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qStf, qRnk)
                .from(qStf)
                .join(qRnk).on(qStf.rnkNo.eq(qRnk.rnkNo))
                .fetchResults();

        return results.getResults();
    }

    public Tuple stfInfo (String stfNo){
        QueryResults<Tuple> results = jpaQueryFactory
                .select(qStf, qRnk,qDpt)
                .from(qStf)
                .join(qRnk).on(qStf.rnkNo.eq(qRnk.rnkNo))
                .join(qDpt).on(qStf.dptNo.eq(qDpt.dptNo))
                .where(qStf.stfNo.eq(stfNo))
                .fetchResults();


        return results.getResults().get(0);
    }
}
