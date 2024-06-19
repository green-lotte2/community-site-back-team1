package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.service.admin.AdminCsService;
import kr.co.zeroPie.service.admin.AdminStfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminCsService adminCsService;
    private final AdminStfService adminStfService;

    // 관리자 메인 페이지
    @GetMapping("/admin/index")
    public String aminMain() {
        return "/admin/index";
    }

    // 공지사항 페이지
    @GetMapping("/admin/cs/notice")
    public String aminNotice() {
        return "/admin/cs/notice";
    }

    // 고객 문의 현황 데이터를 가져오는 엔드포인트
    @GetMapping("/admin/cs/status")
    public ResponseEntity<Map<String, Integer>> getCsStatus() {
        log.info("Fetching CS status...");
        Map<String, Integer> csStatus = adminCsService.getCsStatus();
        log.info("CS status: {}", csStatus);
        return ResponseEntity.ok(csStatus);
    }

    // 관리자 메인 페이지에서 필요한 정보를 가져오는 엔드포인트
    @GetMapping("/admin/userListAndPlan")
    public ResponseEntity<Map<String, Object>> getAdminDashboardInfo(@RequestParam String stfNo) {
        log.info("Fetching dashboard info for stfNo: {}", stfNo);
        Map<String, Object> dashboardInfo = adminStfService.getAdminDashboardInfo(stfNo);
        log.info("Dashboard info: {}", dashboardInfo);
        return ResponseEntity.ok(dashboardInfo);
    }
}
