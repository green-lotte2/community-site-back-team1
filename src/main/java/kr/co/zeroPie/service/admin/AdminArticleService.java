package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.entity.Article;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminArticleService {

    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;
    private final StfRepository stfRepository;

    // 게시물 카테고리 추가
    public ResponseEntity<?> insertArticleCate(ArticleCateDTO articleCateDTO) {
        ArticleCate articleCate = modelMapper.map(articleCateDTO, ArticleCate.class);
        articleCate.setArticleCateName(articleCateDTO.getArticleCateName());
        articleCate.setArticleCateStatus(articleCateDTO.getArticleCateStatus());
        articleCate.setArticleCateRole(articleCateDTO.getArticleCateRole());
        articleCate.setArticleCateCoRole(articleCateDTO.getArticleCateCoRole());

        ArticleCate result = articleCateRepository.save(articleCate);
        ArticleCateDTO resultDTO = modelMapper.map(result, ArticleCateDTO.class);
        if (resultDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 게시물 카테고리 조회 
    public ResponseEntity<?> selectArticleCates() {
        List<ArticleCate> articleCates = (List<ArticleCate>) articleCateRepository.findAll();
       log.info(articleCates.toString());
       return new ResponseEntity<>(articleCates, HttpStatus.OK);
    }

    // 전체 유저 조회
    public ResponseEntity<?> selectUserAll() {
        List<Stf> stfs = (List<Stf>) stfRepository.findAll();

        if (stfs == null || stfs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(stfs, HttpStatus.OK);
    }

}
