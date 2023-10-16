package click.divichart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String index() {
        log.debug("TOP画面表示");
        return "index";
    }
}
