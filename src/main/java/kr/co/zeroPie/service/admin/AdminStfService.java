package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.ArticleCateRepository;
import kr.co.zeroPie.repository.StfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminStfService {

    private final ModelMapper modelMapper;
    private final StfRepository stfRepository;

    // 전체 유저 조회
    public ResponseEntity<?> selectUserAll() {
        List<Stf> stfs = (List<Stf>) stfRepository.findAll();

        if (stfs == null || stfs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stfs, HttpStatus.OK);
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
