package kr.co.zeroPie.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.Cs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CsRepositoryCustom {

    public Page<Cs> CsList(Pageable pageable);//String cate

    public Page<Cs> search(PageRequestDTO pageRequestDTO,Pageable pageable);
}
