package kr.co.zeroPie.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.QCs;
import kr.co.zeroPie.repository.custom.CsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
@RequiredArgsConstructor
public class CsRepositoryImpl implements CsRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QCs qcs = QCs.cs;

    @Override
    public Page<Cs> findByCsCate(Pageable pageable) {//pageRequestDTO.getCsCate(),
        List<Cs> result = jpaQueryFactory
                .select(qcs)
                .from(qcs)
                .fetch();

        long total = result.size();

        return new PageImpl<>(result, pageable, total);

    }
}
