package kr.co.zeroPie.service.page;

import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpSession;
import kr.co.zeroPie.dto.PageDTO;
import kr.co.zeroPie.entity.MyDoc;
import kr.co.zeroPie.entity.Page;
import kr.co.zeroPie.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PageService {

    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;

    // 문서 목록 불러오기
    public ResponseEntity<?> selectPageList (String userId) {
        String modifiedUserId = userId.substring(1, userId.length() - 1);
        List<Tuple> resultList = pageRepository.selectPageListById(modifiedUserId);

        List<Map<String, Object>> pageList = new ArrayList<>();
        for (Tuple result : resultList) {
            Map<String, Object> pageInfo = new HashMap<>();
            pageInfo.put("pno", result.get(0, Integer.class));
            pageInfo.put("title", result.get(1, String.class));
            pageInfo.put("owner", result.get(2, String.class));
            pageList.add(pageInfo);
        }

        log.info("pageList : " + pageList);

        return ResponseEntity.status(HttpStatus.OK).body(pageList);
    }

    // 페이지 데이터 저장
    public void insertPageContent (PageDTO pageDTO) {
        log.info("PageService : " + pageDTO);

        if (pageDTO.getRDate() == null) {
            pageDTO.setRDate(LocalDateTime.now());
        }
        pageRepository.save(modelMapper.map(pageDTO, Page.class));

        Optional<Page> savedPage = pageRepository.findById(pageDTO.getPno());

        if (savedPage.isPresent()) {
            log.info("저장한거 : " + savedPage.get());
        }
    }

    // 페이지 데이터 불러오기
    public ResponseEntity<?> selectPageContent (int pno) {

        Optional<Page> savedPage = pageRepository.findById(pno);

        if (savedPage.isPresent()) {
            log.info("" + savedPage.get());
            return ResponseEntity.status(HttpStatus.OK).body(savedPage.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(1);
        }
    }
}
