package click.divichart.controller;

import click.divichart.bean.dto.CumulativeDividendDto;
import click.divichart.service.CumulativeDividendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 累計配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/cumulativeDividend")
public class CumulativeDividendController {

    private final CumulativeDividendService service;

    @Autowired
    public CumulativeDividendController(CumulativeDividendService cumulativeDividendService) {
        this.service = cumulativeDividendService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model) {
        log.debug("累計配当グラフ表示");

        String[] recentYears = service.getRecentYearsAsc(5);
        String chartData = service.getChartData(recentYears);
        String labels = service.getLabels(recentYears);

        CumulativeDividendDto cumulativeDividendDto = new CumulativeDividendDto(labels, chartData);
        model.addAttribute("cumulativeDividendDto", cumulativeDividendDto);

        return "cumulativeDividend";
    }

}
