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

        Page<StfDTO> qslStfList = stfRepository.searchUserTypeAndKeyword(pageRequestDTO, pageable);

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
        List<Tuple> stfTuple = stfRepository.stfRank();
        log.info(stfTuple.toString());

        List<StfDTO> stfDTOList = new ArrayList<>();
        for (Tuple tuple : stfTuple) {
            Stf stf = tuple.get(0, Stf.class);
            Rnk rnk = tuple.get(1, Rnk.class);

            StfDTO stfDTO = new StfDTO();
            if (stf!=null){
                stfDTO.setStfNo(stf.getStfNo());
                stfDTO.setDptNo(stf.getDptNo());
                stfDTO.setStfName(stf.getStfName());
                stfDTO.setStfRole(stf.getStfRole());
            }
            if (rnk!=null){
                stfDTO.setStrRnkNo(rnk.getRnkName());
            }

            stfDTOList.add(stfDTO);
        }
        log.info(stfDTOList.toString());

        List<Map<String, Object>> formattedDptList = dptList.stream().map(dpt -> {
            Map<String, Object> dptInfo = new HashMap<>();
            dptInfo.put("dptNo", dpt.getDptNo());
            dptInfo.put("dptName", dpt.getDptName());
            dptInfo.put("dptIcon", dpt.getIconName());

            List<Map<String, Object>> members = stfDTOList.stream()
                    .filter(stfDTO -> stfDTO.getDptNo()==(dpt.getDptNo()))
                    .map(stfDTO -> {
                        Map<String, Object> memberInfo = new HashMap<>();
                        memberInfo.put("stfName", stfDTO.getStfName());
                        memberInfo.put("rankNo", stfDTO.getStrRnkNo());
                        memberInfo.put("stfNo", stfDTO.getStfNo());
                        return memberInfo;
                    }).collect(Collectors.toList());
            log.info(members.toString());
            dptInfo.put("member", members);
            return dptInfo;
        }).collect(Collectors.toList());

        log.info("!!!!!!!!!!!!"+formattedDptList.toString());
        return ResponseEntity.status(HttpStatus.OK).body(formattedDptList);

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
      Tuple stfTuple = stfRepository.stfInfo(stfNo);
        Stf stf = stfTuple.get(0, Stf.class);
        Rnk rnk = stfTuple.get(1, Rnk.class);
        Dpt dpt = stfTuple.get(2, Dpt.class);

        StfDTO stfDTO = new StfDTO();
        if (stf !=null){
            stfDTO.setStfNo(stf.getStfNo());
            stfDTO.setStfName(stf.getStfName());
            stfDTO.setStfImg(stf.getStfImg());
            stfDTO.setStfPh(stf.getStfPh());
            stfDTO.setStfEmail(stf.getStfEmail());
        }
        if (rnk !=null){
            stfDTO.setStrRnkNo(rnk.getRnkName());
        }
        if (dpt !=null){
            stfDTO.setStrDptName(dpt.getDptName());
        }

      log.info(String.valueOf(stfDTO));
      return ResponseEntity.ok().body(stfDTO);
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
