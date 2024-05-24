package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.ArticleCateDTO;
import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.service.admin.AdminArticleService;
import kr.co.zeroPie.service.admin.AdminStfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminStfController {

    private final AdminStfService adminStfService;


    // 관리자 - 회원 관리 - 전체 회원 조회
    @GetMapping("/admin/userList")
    public ResponseEntity<?> userList(){

        return adminStfService.selectUserAll();
    }

    // 관리자 - 회원 관리 - 회원 자세히 조회
    @GetMapping("/admin/userDatil")
    public ResponseEntity<?> userDatil(String stfNo){
        return adminStfService.selectUser(stfNo);
    }

    @PutMapping("/admin/user/modify")
    public ResponseEntity<?> userModify(StfDTO stfDTO) {
        return adminStfService.modifyStf(stfDTO);
    }
}
