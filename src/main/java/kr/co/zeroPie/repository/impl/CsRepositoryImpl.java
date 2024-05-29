package kr.co.zeroPie.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.QCs;
import kr.co.zeroPie.repository.custom.CsRepositoryCustom;
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
public class CsRepositoryImpl implements CsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QCs qcs = QCs.cs;


    @Override
    public Page<Cs> CsList(Pageable pageable) {//pageRequestDTO.getCsCate(),
        List<Cs> result = jpaQueryFactory
                .select(qcs)
                .from(qcs)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qcs)
                .fetchCount();

        return new PageImpl<>(result, pageable, total);

    }


    //검색기능
    public Page<Cs> search(PageRequestDTO pageRequestDTO, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();


        // 시작일과 종료일 범위 검색 조건 추가
        if (pageRequestDTO.getStartDate() != null && pageRequestDTO.getEndDate() != null) {
            LocalDate startDate = pageRequestDTO.getStartDate();
            LocalDate endDate = pageRequestDTO.getEndDate().plusDays(1); // 종료일은 포함되어야 하므로 하루를 더합니다.
            builder.and(qcs.csRdate.between(startDate, endDate));
        }

        // 카테고리 검색 조건 추가
        if (pageRequestDTO.getCsCate() != null && !pageRequestDTO.getCsCate().isEmpty()) {
            builder.and(qcs.csCate.eq(pageRequestDTO.getCsCate()));
        }

        // 답변 상태 검색 조건 추가
        if (pageRequestDTO.getCsReply() != null) {
            builder.and(qcs.csReply.eq(Integer.valueOf(pageRequestDTO.getCsReply())));
        }

        // 제목, 제목 + 내용, 내용 검색 타입에 따른 검색어 조건 추가
        if (pageRequestDTO.getType() != null && pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().isEmpty()) {
            String keyword = pageRequestDTO.getKeyword().trim();
            switch (pageRequestDTO.getType()) {
                case "title":
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword));
                    break;
                case "content":
                    builder.and(qcs.csContent.containsIgnoreCase(keyword));
                    break;
                case "title+content":
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword)
                            .or(qcs.csContent.containsIgnoreCase(keyword)));
                    break;
                default:
                    // 기본적으로 제목 검색을 수행합니다.
                    builder.and(qcs.csTitle.containsIgnoreCase(keyword));
                    break;
            }
        }

        QueryResults<Cs> results = jpaQueryFactory
                .selectFrom(qcs)

                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Cs> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable , total);
    }

}
