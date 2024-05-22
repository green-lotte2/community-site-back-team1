package kr.co.zeroPie.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AdminController {

    @GetMapping("/admin/index")
    public String aminMain() {
        return "/admin/index";
    }

    @GetMapping("/admin/user/list")
    public String aminUserset() {
        return "/admin/user/list";
    }

    @GetMapping("/admin/cs/notice")
    public String aminNotice() {
        return "/admin/cs/notice";
    }
}
