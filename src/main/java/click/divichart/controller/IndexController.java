package click.divichart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/index")
public class IndexController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String index() {
        log.debug("TOP画面表示");
        return "index";
    }
}
