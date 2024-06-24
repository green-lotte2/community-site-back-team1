package kr.co.zeroPie.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.*;
import kr.co.zeroPie.repository.custom.StfRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    public Page<StfDTO> searchUserTypeAndKeyword(PageRequestDTO pageRequestDTO, Pageable pageable){

        QueryResults<Tuple> results = jpaQueryFactory
                .select(qStf, qRnk,qDpt)
                .from(qStf)
                .join(qRnk).on(qStf.rnkNo.eq(qRnk.rnkNo))
                .join(qDpt).on(qStf.dptNo.eq(qDpt.dptNo))
                .where(
                    stfStatusEq(pageRequestDTO.getStfStatus()),
                        rnkNoEq(pageRequestDTO.getRnkNo()),
                        dptNoEq(pageRequestDTO.getDptNo()),
                        stfEntBetween(pageRequestDTO.getStartDate2(), pageRequestDTO.getEndDate2()),//localDtaeTime으로 변경되었습니다.
                        keywordContains(pageRequestDTO.getType(), pageRequestDTO.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(qStf.stfEnt.desc())
                .fetchResults();

        List<Tuple> content = results.getResults();
        List<StfDTO> stfDTOList = new ArrayList<>();

        for (Tuple tuple : content) {
            Stf stf = tuple.get(qStf);
            Rnk rnk = tuple.get(qRnk);
            Dpt dpt = tuple.get(qDpt);

            StfDTO stfDTO = new StfDTO();
            if (stf!=null){
                stfDTO.setStfName(stf.getStfName());
                stfDTO.setStfNo(stf.getStfNo());
                stfDTO.setStfPh(stf.getStfPh());
                stfDTO.setStfImg(stf.getStfImg());
                stfDTO.setStfStatus(stf.getStfStatus());
                stfDTO.setStfEnt(stf.getStfEnt());
                stfDTO.setStfEmail(stf.getStfEmail());
            }
            if (rnk!=null){
                stfDTO.setStrRnkNo(rnk.getRnkName());
            }else {
                stfDTO.setStrRnkNo("미분류");
            }
            if (dpt!=null){
                stfDTO.setStrDptName(dpt.getDptName());
            }

            stfDTOList.add(stfDTO);
        }
        long total = results.getTotal();
        return new PageImpl<>(stfDTOList, pageable, total);
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
                .orderBy(qStf.rnkNo.desc())
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
                .orderBy(qStf.rnkNo.desc())
                .fetchResults();


        return results.getResults().get(0);
    }

    //부서 선택
    @Override
    public Dpt dptSelect(StfDTO stfDTO) {

        log.info("stfRepository - dptSelect - stfDTO : "+stfDTO);

        Dpt dtpSelect = jpaQueryFactory
                .select(qDpt)
                .from(qDpt)
                .where(qDpt.dptNo.eq(stfDTO.getDptNo()))
                .fetchOne();

        return dtpSelect;
    }

    //유저 카운트(가용회원)
    public Long countByUser(){

        log.info("stfRepository - countByUser");

        Long count = jpaQueryFactory
                .select(qStf.count())
                .from(qStf)
                .where(qStf.stfStatus.eq("Active").or(qStf.stfStatus.eq("Break")))
                .fetchOne();

        return count;

    }

    public List<Stf> findStfRole(){
        List<Stf> AdminRole = jpaQueryFactory
                .select(qStf)
                .from(qStf)
                .where(qStf.stfRole.eq("ADMIN"))
                .fetch();

        return AdminRole;

    }
}
