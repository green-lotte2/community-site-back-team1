package kr.co.zeroPie.repository.custom;


import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Rnk;
import kr.co.zeroPie.entity.Stf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StfRepositoryCustom {

    public Page<StfDTO> searchUserTypeAndKeyword(PageRequestDTO pageRequestDTO, Pageable pageable);

    public List<Tuple> stfRank ();

    public Tuple stfInfo (String stfNo);

    public Dpt dptSelect(StfDTO stfDTO);

    public Long countByUser();

    public List<Stf> findStfRole();

}
