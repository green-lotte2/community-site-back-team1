package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.entity.ArticleCate;
import kr.co.zeroPie.service.admin.AdminArticleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class AdminArticleControllerTest {

    @Autowired
    private AdminArticleService adminArticleService;
    @Autowired
    private SpringTemplateEngine templateEngine;

   

}