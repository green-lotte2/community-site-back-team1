package kr.co.zeroPie.service.admin;

import com.querydsl.core.Tuple;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminStfService {

    private final ModelMapper modelMapper;
    private final StfRepository stfRepository;
    private final DptRepository dptRepository;
    private final RnkRepository rnkRepository;

    // 전체 유저 조회(검색기능)
    public ResponseEntity<?> selectUserAll(@RequestBody PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("stfNo");
        /*
        Page<Stf> stfs = stfRepository.findAll(pageable);
        List<Stf> stfList = stfs.getContent();
        int total = (int) stfs.getTotalElements();


        List<StfDTO> stfDTOList = new ArrayList<>();
        for (Stf stf : stfList) {
            stfDTOList.add(modelMapper.map(stf, StfDTO.class));
        }
        log.info(stfDTOList.toString());
        */

        Page<Stf> qslStfList = stfRepository.searchUserTypeAndKeyword(pageRequestDTO, pageable);
        List<StfDTO> stfDTOList = qslStfList.getContent().stream()
                .map(stf -> modelMapper.map(stf, StfDTO.class))
                .toList();
        int total = (int) qslStfList.getTotalElements();
        log.info(stfDTOList.toString());

        PageResponseDTO pageResponseDTO = PageResponseDTO.<StfDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(stfDTOList)
                .total(total)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDTO);
    }

    public ResponseEntity<?> userList(){
        List<Stf> stfList = stfRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(stfList);
    }

    // 부서 및 멤버 조회
    public ResponseEntity<?> selectStfAndDptList(){
        List<Dpt> dptList = dptRepository.findAll();
        List<StfDTO> stfList = stfRepository.stfRank();
        log.info(stfList.toString());
/*
        List<Map<String, Object>> formattedDptList = dptList.stream().map(dpt -> {
            Map<String, Object> dptInfo = new HashMap<>();
            dptInfo.put("dptNo", dpt.getDptNo());
            dptInfo.put("dptName", dpt.getDptName());

            List<Map<String, Object>> members = stfList.stream()
                    .filter(stf -> stf.getDptNo()==(dpt.getDptNo()))
                    .map(stf -> {
                        Map<String, Object> memberInfo = new HashMap<>();
                        memberInfo.put("stfName", stf.getStfName());
                        memberInfo.put("rankNo", stf.getRnkNo());
                        return memberInfo;
                    }).collect(Collectors.toList());

            dptInfo.put("member", members);
            return dptInfo;
        }).collect(Collectors.toList());

        log.info("!!!!!!!!!!!!"+formattedDptList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(formattedDptList);

 */
        return ResponseEntity.status(HttpStatus.OK).body(0);
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


    public ResponseEntity<?> updateStf(StfDTO stfDTO){
        String stfNo = stfDTO.getStfNo();
        Optional<Stf> optionalStf = stfRepository.findById(stfNo);
        Stf stf = optionalStf.get();

        stf.setStfRole(stfDTO.getStfRole());
        stf.setDptNo(stfDTO.getDptNo());
        stf.setRnkNo(stfDTO.getRnkNo());
        stf.setStfStatus(stfDTO.getStfStatus());
        stfRepository.save(stf);
        log.info(stf.toString());
        return ResponseEntity.ok().body(stf);
    }

}
