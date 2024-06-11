package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.service.admin.AdminConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminConfigController {

    private final AdminConfigService adminConfigService;

    @DeleteMapping("/admin/deleteDpt")
    public void deleteDpt (@RequestParam("dptNo") int dptNo){
        log.info("dptNo"+dptNo);
        adminConfigService.deleteDpt(dptNo);
    }

    @PutMapping("/admin/updateDpt")
    public DptDTO updateDpt(@RequestBody DptDTO dptDTO){
        log.info("dptNo"+ dptDTO);
        return adminConfigService.updateDpt(dptDTO);
    }
}
