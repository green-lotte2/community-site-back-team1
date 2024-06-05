package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.ArticleCateRepository;
import kr.co.zeroPie.repository.ArticleRepository;
import kr.co.zeroPie.repository.StfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminArticleService {

    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;
    private final ArticleRepository articleRepository;

    // 게시물 카테고리 추가
    @CacheEvict(value = "cateCache", allEntries = true)
    public ResponseEntity<?> insertArticleCate(ArticleCateDTO articleCateDTO) {
        ArticleCate articleCate = modelMapper.map(articleCateDTO, ArticleCate.class);
        articleCate.setArticleCateName(articleCateDTO.getArticleCateName());
        articleCate.setArticleCateOutput(articleCateDTO.getArticleCateOutput());
        articleCate.setArticleCateStatus(articleCateDTO.getArticleCateStatus());
        articleCate.setArticleCateVRole(articleCateDTO.getArticleCateVRole());
        articleCate.setArticleCateWRole(articleCateDTO.getArticleCateWRole());
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
    @Cacheable("cateCache")
    public ResponseEntity<?> selectArticleCates() {
        List<ArticleCate> articleCates = (List<ArticleCate>) articleCateRepository.findAll();
        List<ArticleCateDTO> articleCateDTOList = new ArrayList<>();
        for(ArticleCate articleCate : articleCates) {
            int articleCount = articleRepository.countByArticleCateNo(articleCate.getArticleCateNo());
            ArticleCateDTO articleCateDTO = ArticleCateDTO.builder()
                    .articleCateNo(articleCate.getArticleCateNo())
                    .articleCateName(articleCate.getArticleCateName())
                    .articleCateStatus(articleCate.getArticleCateStatus())
                    .articleCateVRole(articleCate.getArticleCateVRole())
                    .articleCateWRole(articleCate.getArticleCateWRole())
                    .articleCateCoRole(articleCate.getArticleCateCoRole())
                    .articleCount(articleCount)
                    .build();
            articleCateDTOList.add(articleCateDTO);
        }
       log.info(articleCateDTOList.toString());
       return new ResponseEntity<>(articleCateDTOList, HttpStatus.OK);
    }

    // 게시물 카테고리 삭제
    @CacheEvict(value = "cateCache", allEntries = true)
    public ResponseEntity<?> deleteArticleCate(int articleCateNo) {
        try {
            articleCateRepository.deleteById(articleCateNo);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
        }
    }

    // 게시물 카테고리 수정
    @CacheEvict(value = "cateCache", allEntries = true)
    public ResponseEntity<?> modifyArticleCate(ArticleCateDTO articleCateDTO) {
        int articleCateNo = articleCateDTO.getArticleCateNo();

        return articleCateRepository.findById(articleCateNo)
                .map(articleCate -> updateArticleCate(articleCate, articleCateDTO))
                .map(updatedArticleCate -> ResponseEntity.ok().body(updatedArticleCate))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    private ArticleCate updateArticleCate(ArticleCate articleCate, ArticleCateDTO articleCateDTO) {
        articleCate.setArticleCateName(articleCateDTO.getArticleCateName());
        articleCate.setArticleCateStatus(articleCateDTO.getArticleCateStatus());
        articleCate.setArticleCateVRole(articleCateDTO.getArticleCateVRole());
        articleCate.setArticleCateWRole(articleCateDTO.getArticleCateWRole());
        articleCate.setArticleCateCoRole(articleCateDTO.getArticleCateCoRole());
        return articleCateRepository.save(articleCate);
    }

}



