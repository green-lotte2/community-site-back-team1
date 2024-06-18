package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.dto.RnkDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Rnk;
import kr.co.zeroPie.repository.DptRepository;
import kr.co.zeroPie.repository.RnkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminConfigService {
    private final ModelMapper modelMapper;
    private final DptRepository dptRepository;
    private final RnkRepository rnkRepository;

    public void deleteDpt(int dptNo){
        dptRepository.findById(dptNo)
                .ifPresent(dptRepository::delete);
    }

    public ResponseEntity<?> deleteRnk(int rnkNo){
        try {
            rnkRepository.findById(rnkNo).ifPresent(rnkRepository::delete);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public DptDTO updateDpt(DptDTO dptDTO) {
        int dptNo = dptDTO.getDptNo();
        Optional<Dpt> optDpt = dptRepository.findById(dptNo);
        if (optDpt.isPresent()) {
            Dpt dpt = optDpt.get();
            dpt.setDptName(dptDTO.getDptName());
            dpt =  dptRepository.save(dpt);
            return new DptDTO(dpt.getDptNo(), dpt.getDptName(), dpt.getDptCode(), dpt.getIconName());
        } else {
            log.info("Dpt not found with id: " + dptNo);
            return null;
        }
    }

    @Transactional
    public List<RnkDTO> updateRank(List<RnkDTO> rnkDTOList) {
        return rnkDTOList.stream().map(rankDTO -> {
            Rnk rank = rnkRepository.findById(rankDTO.getRnkNo()).orElse(new Rnk());
            rank.setRnkNo(rankDTO.getRnkNo());
            rank.setRnkName(rankDTO.getRnkName());
            rank.setRnkIndex(rankDTO.getRnkIndex());

            Rnk savedRank = rnkRepository.save(rank);

            return new RnkDTO(savedRank.getRnkNo(), savedRank.getRnkName(), savedRank.getRnkIndex());
        }).collect(Collectors.toList());
    }

    // dpt 생성
    public ResponseEntity<?> insertRnk(RnkDTO rnkDTO){
        Rnk rnk = modelMapper.map(rnkDTO, Rnk.class);
        Rnk savedRnk = rnkRepository.save(rnk);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRnk);
    }
}
