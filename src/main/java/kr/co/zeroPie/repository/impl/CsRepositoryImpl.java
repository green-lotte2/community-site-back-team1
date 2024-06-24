package kr.co.zeroPie.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.QCs;
import kr.co.zeroPie.entity.QStf;
import kr.co.zeroPie.repository.custom.CsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
@Slf4j
@RequiredArgsConstructor
public class CsRepositoryImpl implements CsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QCs qcs = QCs.cs;
    QStf qStf = QStf.stf;


    @Override
    public Page<Cs> CsList(Pageable pageable) {//pageRequestDTO.getCsCate(),

        log.info("pageable : "+pageable);

        List<Cs> result = jpaQueryFactory
                .select(qcs)
                .from(qcs)
                .orderBy(qcs.csRdate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Query for fetching the total count
        long total = jpaQueryFactory
                .selectFrom(qcs)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);

    }


    //검색기능
    public Page<Cs> search(PageRequestDTO pageRequestDTO, Pageable pageable) {

        log.info("search ....1 - pageRequestDTO : "+pageRequestDTO);//여기에 아이디가 있음!
        log.info("search ....2 - pageable : "+pageable);

        BooleanBuilder builder = new BooleanBuilder();//where 절 저장
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();//orderby절 저장

        DateTimePath<LocalDateTime> csRdatePath = null;

        if(pageRequestDTO.getStartDate() != null || pageRequestDTO.getEndDate() != null){//날짜 값이 있기라도 하면
            // LocalDateTime 타입의 Expression을 생성
            csRdatePath = new PathBuilder<>(Cs.class, "cs").getDateTime("csRdate", LocalDateTime.class);
        }


        // 시작일과 종료일 범위 검색 조건 추가
        if (pageRequestDTO.getStartDate() != null && pageRequestDTO.getEndDate() != null) {
            log.info("여기는 시작일과 종료일이 있을 때 들어오는곳이야");

            // 시작일과 종료일을 LocalDateTime으로 변환
            LocalDateTime startDate = pageRequestDTO.getStartDate();
            LocalDateTime endDate = pageRequestDTO.getEndDate();

            builder.and(csRdatePath.between(startDate, endDate.plusDays(1)));

        }else if(pageRequestDTO.getStartDate() == null && pageRequestDTO.getEndDate() != null){

            log.info("여기는 종료일만 있을 때 들어오는 곳이야.");

            LocalDateTime endDate = pageRequestDTO.getEndDate();

            builder.and(csRdatePath.before(endDate.plusDays(1)));

        }else if(pageRequestDTO.getStartDate() != null && pageRequestDTO.getEndDate() == null){

            log.info("여기는 시작일만 있을 때 들어오는 곳이야.");

            LocalDateTime startDate = pageRequestDTO.getStartDate();
            LocalDateTime endDate = LocalDateTime.now();//오늘 현재날짜를 받음

            builder.and(csRdatePath.between(startDate, endDate.plusDays(1)));
        }

        // 카테고리 검색 조건 추가
        if (pageRequestDTO.getCsCate() != null && !pageRequestDTO.getCsCate().isEmpty()) {
            log.info("여기는 카테고리 검색 조건이 있으면 들어오는곳이야");

            log.info("카테고리 값 : "+pageRequestDTO.getCsCate());

            builder.and(qcs.csCate.eq(pageRequestDTO.getCsCate()));

            log.info(builder.toString());
        }

        // 답변 상태 검색 조건 추가
        if (pageRequestDTO.getCsReply() != null && !pageRequestDTO.getCsReply().isEmpty()) {
            log.info("여기에 일단 들어오나?여기는 답변상태 쪽이야.");
            try {
                builder.and(qcs.csReply.eq(Integer.valueOf(pageRequestDTO.getCsReply())));
            } catch (NumberFormatException e) {

                //이 예외처리가 없으면 NumberFormatException이 에러가 뜸
                throw new IllegalArgumentException("Invalid csReply value: " + pageRequestDTO.getCsReply(), e);
            }
        }

        // 제목, 제목 + 내용, 내용 검색 타입에 따른 검색어 조건 추가
        if (pageRequestDTO.getType() != null && pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().isEmpty()) {
            String keyword = pageRequestDTO.getKeyword().trim();
            switch (pageRequestDTO.getType()) {
                case "title":
                    log.info("제목으로 검색했네?");
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword));
                    break;
                case "content":
                    log.info("내용으로 검색했네?");
                    builder.and(qcs.csContent.containsIgnoreCase(keyword));
                    break;
                case "title+content":
                    log.info("제목+내용으로 검색했네?");
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword)
                            .or(qcs.csContent.containsIgnoreCase(keyword)));
                    break;
                case "writer":
                    log.info("글쓴이로 검색했네?");
                    builder.and(qcs.stfName.containsIgnoreCase(keyword));
                    break;
                default:
                    // 기본적으로 제목 검색을 수행합니다.
                    log.info("디폴트는 제목검색이징");
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword));
                    break;
            }
        }

        if(pageRequestDTO.getLatest().isEmpty() && pageRequestDTO.getHit().isEmpty() ){

            log.info("처음에는 여기에 들어와야해");

            orderSpecifiers.add(qcs.csRdate.desc());

        }

        //최신순
        if(pageRequestDTO.getLatest()!=null && !pageRequestDTO.getLatest().isEmpty()){
            if(pageRequestDTO.getLatest().equals("1")){//pageRequestDTO.getLatest()=="1"과 같은거래

                log.info("여기는 최신순 조건이 있으면 들어오는곳이야");
                orderSpecifiers.add(qcs.csRdate.desc());
            }else{
                log.info("최신순 클릭 안함.");
            }

        }

        //조회순
        if(pageRequestDTO.getHit()!=null && !pageRequestDTO.getHit().isEmpty()){
            log.info("여기는 조회순 조건이 있으면 들어오  는곳이야");

            if(Objects.equals(pageRequestDTO.getHit(), "1")){

                log.info("여기는 최신순 조건이 있으면 들어오는곳이야!!!!!");
                orderSpecifiers.add(qcs.csHit.desc());
            }else{
                log.info("조회순 클릭 안함.");
            }

        }

        /*
        // 조건 추가: 관리자 또는 매니저이거나 자신이 작성한 글인 경우 비밀글 포함
        if ("ADMIN".equals(pageRequestDTO.getStfRole()) || "MANAGER".equals(pageRequestDTO.getStfRole())) {
            builder.and(qcs.secret.eq("비밀글"));
        } else {
            builder.and(qcs.secret.ne("비밀글")
                    .or(qcs.secret.eq("비밀글").and(qcs.stfNo.eq(pageRequestDTO.getStfNo()))));
        }*/

        if ("ADMIN".equals(pageRequestDTO.getStfRole()) || "MANAGER".equals(pageRequestDTO.getStfRole())) {//관리자 들은 모든 글을 볼 수 있게
            builder.and(qcs.secret.isNull().or(qcs.secret.eq("전체공개")).or(qcs.secret.eq("비밀글")));
        } else {
            // 일반 사용자는 비밀글이 아닌 글과 자신이 작성한 비밀글만 검색
            builder.and(
                    qcs.secret.isNull()
                            .or(qcs.secret.ne("비밀글"))
                            .or(qcs.secret.eq("비밀글").and(qcs.stfNo.eq(pageRequestDTO.getStfNo())))
            );
        }


        QueryResults<Cs> results = jpaQueryFactory
                .selectFrom(qcs)
                .where(builder)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier<?>[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Cs> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable , total);
    }

}
