package kr.co.zeroPie.controller.admin;

import kr.co.zeroPie.dto.DptDTO;
import kr.co.zeroPie.dto.RnkDTO;
import kr.co.zeroPie.entity.Dpt;
import kr.co.zeroPie.entity.Rnk;
import kr.co.zeroPie.service.admin.AdminConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/admin/updateRank")
    public List<RnkDTO> updateRank(@RequestBody List<RnkDTO> RnkDTOList){
        log.info("updateRank"+RnkDTOList);
        return adminConfigService.updateRank(RnkDTOList);
    }

    @PostMapping("/admin/insertRank")
    public ResponseEntity<?> insertRnk(@RequestBody RnkDTO rnkDTO){
        log.info("insertRnk"+rnkDTO);
        return adminConfigService.insertRnk(rnkDTO);
    }

    @DeleteMapping("/admin/deleteRank")
    public ResponseEntity<?> deleteRank (@RequestParam("rnkNo") int rnkNo){
        log.info("rnkNo"+rnkNo);
        return adminConfigService.deleteRnk(rnkNo);
    }
}
