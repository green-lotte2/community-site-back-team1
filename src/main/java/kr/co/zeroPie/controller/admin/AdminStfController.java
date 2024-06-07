package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.*;
import kr.co.zeroPie.service.admin.AdminArticleService;
import kr.co.zeroPie.service.admin.AdminStfService;
import kr.co.zeroPie.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminStfController {

    private final AdminStfService adminStfService;
    private final CustomFileUtil fileUtil;


    // 관리자 - 회원 관리 - 전체 회원 조회
    @PostMapping("/admin/user/list")
    public ResponseEntity<?> userList(@RequestBody PageRequestDTO pageRequestDTO){

        log.info(pageRequestDTO);

        return adminStfService.selectUserAll(pageRequestDTO);
    }

    @GetMapping("/admin/user/dptAndStfList")
    public ResponseEntity<?> dptAndStfList(){

        return adminStfService.selectStfAndDptList();
    }

    @GetMapping("/admin/user/list")
    public ResponseEntity<?> sftList(){

        return adminStfService.userList();
    }

    // 관리자 - 회원 관리 - 전체 부서 조회
    @GetMapping("/admin/user/dptList")
    public ResponseEntity<?> dptList(){
        return adminStfService.selectDptList();
    }

    @PostMapping("/admin/dpt/insert")
    public ResponseEntity<?> insertDpt(@RequestBody DptDTO dptDTO){
        log.info(dptDTO);
        return adminStfService.insertDpt(dptDTO);
    }

    // 관리자 - 회원 관리 - 전체 직급 조회
    @GetMapping("/admin/user/rnkList")
    public ResponseEntity<?> rnkList(){
        return adminStfService.selectRnkList();
    }


    // 관리자 - 회원 관리 - 회원 자세히 조회
    @GetMapping("/admin/user/detail")
    public ResponseEntity<?> userDetail(@RequestParam("stfNo") String stfNo){
        log.info("stfNo : "+ stfNo);
        return adminStfService.selectUser(stfNo);
    }

    @PutMapping("/admin/user/modify")
    public ResponseEntity<?> userModify(@RequestBody StfDTO stfDTO) {
        log.info("변경"+stfDTO);
        return adminStfService.updateStf(stfDTO);
    }

    @GetMapping("/user/thumb/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        return fileUtil.getFile(fileName);
    }
}
