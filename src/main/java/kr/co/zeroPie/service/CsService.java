package kr.co.zeroPie.service;


import kr.co.zeroPie.dto.CsDTO;
import kr.co.zeroPie.dto.PageRequestDTO;
import kr.co.zeroPie.dto.PageResponseDTO;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.repository.CsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class CsService {

    private final CsRepository csRepository;

    private final ModelMapper modelMapper;


    public ResponseEntity<?> list(PageRequestDTO pageRequestDTO) {

        log.info("pageRequestDTO : " + pageRequestDTO);
        log.info("pageRequestDTO : " + pageRequestDTO.getCsCate());

        Pageable pageable = pageRequestDTO.getPageable("csNo");

        Page<Cs> pageArticle = csRepository.findByCsCate(pageRequestDTO.getCsCate(), pageable);
        log.info("pageArticle : " + pageArticle.getContent());

        if (!pageArticle.getContent().isEmpty()) {
            List<CsDTO> dtoList = pageArticle.getContent().stream()
                    .map(entity -> {
                        CsDTO dto = modelMapper.map(entity, CsDTO.class);
                        dto.setStfNo(entity.getStfNo());
                        return dto;
                    })
                    .toList();

            int total = (int) pageArticle.getTotalElements();

            PageResponseDTO<CsDTO> responseDTO = PageResponseDTO.<CsDTO>builder()
                    .dtoList(dtoList)
                    .pageRequestDTO(pageRequestDTO)
                    .total(total)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }
}
