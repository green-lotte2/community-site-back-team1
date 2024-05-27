package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.dto.PageResponseDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Rnk;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.DptRepository;
import kr.co.zeroPie.repository.RnkRepository;
import kr.co.zeroPie.repository.StfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminStfService {

    private final ModelMapper modelMapper;
    private final StfRepository stfRepository;
    private final DptRepository dptRepository;
    private final RnkRepository rnkRepository;

    // 전체 유저 조회
    public ResponseEntity<?> selectUserAll(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("stfNo");
        Page<Stf> stfs = stfRepository.findAll(pageable);
        List<Stf> stfList = stfs.getContent();
        int total = (int) stfs.getTotalElements();

        List<StfDTO> stfDTOList = new ArrayList<>();
        for (Stf stf : stfList) {
            stfDTOList.add(modelMapper.map(stf, StfDTO.class));
        }
        log.info(stfDTOList.toString());

        PageResponseDTO pageResponseDTO = PageResponseDTO.<StfDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(stfDTOList)
                .total(total)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
    }

    // 부서 조회
    public ResponseEntity<?> selectDptList(){
        List<Dpt> dptList = dptRepository.findAll();
        log.info(dptList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(dptList);
    }

    // 부서 조회
    public ResponseEntity<?> selectRnkList(){

        List<Rnk> rnkList = rnkRepository.findAll();
        log.info(rnkList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(rnkList);
    }

    // 유저 디테일 조회
    public ResponseEntity<?> selectUser(String stfNo) {
      Optional<Stf> stf = stfRepository.findById(stfNo);
      return ResponseEntity.ok().body(stf);
    }

    public ResponseEntity<?> modifyStf(StfDTO stfDTO) {
        String stfNo = stfDTO.getStfNo();

        return stfRepository.findById(stfNo)
                .map(stf -> {
                    Stf updatedStf = updateStf(stf, stfDTO);
                    return ResponseEntity.ok().body(updatedStf);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public Stf updateStf(Stf stf, StfDTO stfDTO){
        stf.setStfNo(stfDTO.getStfNo());
        stf.setStfName(stfDTO.getStfName());
        stf.setStfPass(stfDTO.getStfPass());
        stf.setStfPh(stfDTO.getStfPh());
        stf.setStfZip(stfDTO.getStfZip());
        stf.setStfAddr1(stf.getStfAddr1());
        stf.setStfAddr2(stf.getStfAddr2());
        stf.setStfEmail(stfDTO.getStfEmail());
        stf.setStfRole(stfDTO.getStfRole());
        stf.setDptNo(stf.getDptNo());
        stf.setRnkNo(stf.getRnkNo());
        stf.setPlanStatusNo(stf.getPlanStatusNo());
        return stfRepository.save(stf);
    }

}
