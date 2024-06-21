package kr.co.zeroPie.service;


import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import kr.co.zeroPie.dto.*;
import kr.co.zeroPie.entity.Cs;
import kr.co.zeroPie.entity.CsComment;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.CsCommentRepository;
import kr.co.zeroPie.repository.CsRepository;
import kr.co.zeroPie.repository.StfRepository;
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
import org.springframework.web.bind.annotation.RequestBody;


import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Log4j2
@RequiredArgsConstructor
@Service
public class CsService {

    private final CsRepository csRepository;
    private final CsCommentRepository csCommentRepository;
    private final StfRepository stfRepository;
    private final ModelMapper modelMapper;


    //고객센터 리스트 출력
    public ResponseEntity<?> list(PageRequestDTO pageRequestDTO) {

        log.info("pageRequestDTO : " + pageRequestDTO);
        log.info("pageRequestDTO : " + pageRequestDTO.getCsCate());

        Pageable pageable = pageRequestDTO.getPageable("csNo");

        Page<Cs> pageArticle = csRepository.CsList(pageable);//pageRequestDTO.getCsCate(),
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


    //고객센터 게시글 등록
    public void csRegister(CsDTO csDTO){

        log.info("csDTO : " + csDTO);

        csDTO.setCsReply(0);//이거 안넣으면 에러남article

        Optional<Stf> optStf = stfRepository.findById(csDTO.getStfNo());

        Stf stf = modelMapper.map(optStf,Stf.class);

        csDTO.setStfName(stf.getStfName());//사용자 이름을 넣음

        csRepository.save(modelMapper.map(csDTO, Cs.class));//받은 정보들 저장
    }

    //고객센터 게시글 보기
    public ResponseEntity<?> csView(int csNo){

        Optional<?> optionalCs = csRepository.findById(csNo);

        if (optionalCs.isPresent()) {
            Cs cs = modelMapper.map(optionalCs.get(),Cs.class);//게시글번호에 해당하는 게시글 데이터

            int changeHit = cs.getCsHit();

            cs.setCsHit(changeHit+1);//히트 수 +1해주기

            csRepository.save(cs);

            CsDTO csDTO = modelMapper.map(cs,CsDTO.class);


            return ResponseEntity.status(HttpStatus.OK).body(csDTO);
        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    /*
    //고객센터 게시글 수정하기
    public ResponseEntity<?> csModify(CsDTO csDTO){

        log.info("csModify - csDTO : " + csDTO);

        LocalDate now = LocalDate.now();

        csDTO.setCsRdate(now);

        csRepository.save(modelMapper.map(csDTO, Cs.class));

        return ResponseEntity.status(HttpStatus.OK).body(csDTO);

    }


     */
    //고객센터 게시글 삭제하기
    @Transactional
    public ResponseEntity<?> csDelete(int csNo){

        log.info("csDelete - csNo : " + csNo);

        log.info(" 게시글 삭제 전. 답변 삭제 전");

        csCommentRepository.deleteByCsNo(csNo);//게시글에 달린 답변들 삭제

        log.info("게시글에 달린 답변 삭제 한 후 ");

        csRepository.deleteById(csNo);//게시글 삭제

        log.info("게시글 삭제 한 후 ");

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }

    public ResponseEntity<?> csAnswer(CsCommentDTO csCommentDTO){

        log.info("여기는 csService - csAnswer");

        log.info("csCommentDTO : " + csCommentDTO);

        csCommentRepository.save(modelMapper.map(csCommentDTO, CsComment.class));//게시글에 대한 답변

        //답변을 달았으면 cs의 reply를 count해줌
        Optional<?> optionalCs = csRepository.findById(csCommentDTO.getCsNo());

        if (optionalCs.isPresent()) {

            Cs cs = modelMapper.map(optionalCs, Cs.class);

            int aws = cs.getCsReply();
            cs.setCsReply(aws+1);//답변을 달때마다 카운트 하나씩 늘림

            csRepository.save(cs);

            return ResponseEntity.status(HttpStatus.OK).body(csCommentDTO);

        }else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }


    //검색을 위한 곳
    public ResponseEntity<?> search(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        log.info("csService - search - pageRequestDTO : " + pageRequestDTO);

       Page<Cs> pageArticle = csRepository.search(pageRequestDTO,pageable);

       log.info("CsService - search - pageArticle"+pageArticle);

        if (!pageArticle.getContent().isEmpty()) {
            List<CsDTO> dtoList = pageArticle.getContent().stream()
                    .map(entity -> {
                        CsDTO dto = modelMapper.map(entity, CsDTO.class);
                        dto.setStfNo(entity.getStfNo());
                        return dto;
                    })
                    .toList();


            log.info("csService - search - dtoList 찍어본다 :"+dtoList);

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
    
    
    //댓글 가져오기
    public ResponseEntity<List<CsCommentDTO>> csAnswerList(int csNo){

        log.info("csService - csAnswerList - csNo : " + csNo);
        List<Tuple> csCommentList = csCommentRepository.findByCsNo(csNo);

        List<CsCommentDTO> dtoList = csCommentList.stream()
                .map(entity -> {
                    CsComment csComment = entity.get(0,CsComment.class);
                    CsCommentDTO dto = modelMapper.map(csComment, CsCommentDTO.class);
                    dto.setStfName(entity.get(1,String.class));//name 값 가져오기
                    dto.setStfImg(entity.get(2,String.class));//img값 가져오기
                    return dto;
                })
                .toList();

        log.info("뭐 찍히는지 보자"+dtoList);

        return  ResponseEntity.status(HttpStatus.OK).body(dtoList);
        
    }

    //댓글 삭제
    public ResponseEntity<?> answerDelete(int csComNo){

        //댓글 삭제전에 csReply -1해주기
        Optional<?> optCsComment= csCommentRepository.findById(csComNo);

        Cs csComment =  modelMapper.map(optCsComment,Cs.class);

        int csNo = csComment.getCsNo();//이걸로 csReply 찾기

        Optional<?> findCs = csRepository.findById(csNo);

        Cs cs = modelMapper.map(findCs,Cs.class);

        int changeReply = cs.getCsReply();

        cs.setCsReply(changeReply-1);

        csRepository.save(cs);//reply-1하고 저장
        
        
        //댓글을 삭제
        csCommentRepository.deleteById(csComNo);

        return ResponseEntity.status(HttpStatus.OK).body(1);
    }
}
