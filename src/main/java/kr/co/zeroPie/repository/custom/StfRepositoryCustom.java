package kr.co.zeroPie.repository.custom;


import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.entity.Stf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StfRepositoryCustom {

    public Page<Stf> searchUserTypeAndKeyword(PageRequestDTO pageRequestDTO, Pageable pageable);

}
