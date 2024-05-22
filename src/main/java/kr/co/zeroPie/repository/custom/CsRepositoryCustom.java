package kr.co.zeroPie.repository.custom;

import kr.co.zeroPie.entity.Cs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CsRepositoryCustom {

    public Page<Cs> findByCsCate(Pageable pageable);//String cate,
}
