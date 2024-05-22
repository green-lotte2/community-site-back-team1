package kr.co.zeroPie.service.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.entity.Article;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.repository.ArticleCateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminArticleService {

    private final ModelMapper modelMapper;
    private final ArticleCateRepository articleCateRepository;

    // 게시물 카테고리 추가
    public ArticleCate insertArticleCate(ArticleCateDTO articleCateDTO) {
        ArticleCate articleCate = modelMapper.map(articleCateDTO, ArticleCate.class);
        articleCate.setArticleCateName(articleCateDTO.getArticleCateName());
        articleCate.setArticleCateStatus(articleCateDTO.getArticleCateStatus());
        articleCate.setArticleCateRole(articleCateDTO.getArticleCateRole());
        articleCate.setArticleCateCoRole(articleCateDTO.getArticleCateCoRole());

        return articleCateRepository.save(articleCate);
    }

    // 게시물 카테고리 조회 
    public void selectArticleCates() {
        Iterable<ArticleCate> articleCates = articleCateRepository.findAll();
       log.info(articleCates.toString());
    }
}
