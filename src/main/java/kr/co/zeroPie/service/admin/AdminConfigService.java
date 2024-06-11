package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.repository.DptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminConfigService {
    private final DptRepository dptRepository;

    public void deleteDpt(int dptNo){
        dptRepository.findById(dptNo)
                .ifPresent(dptRepository::delete);
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
}
