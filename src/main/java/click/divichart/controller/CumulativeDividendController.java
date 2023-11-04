package click.divichart.controller;

import click.divichart.bean.dto.CumulativeDividendDto;
import click.divichart.bean.form.CumulativeDividendForm;
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

    @Autowired
    CumulativeDividendService service;

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, CumulativeDividendForm cumulativeDividendForm) {
        log.debug("累計配当グラフ表示");

        String[] recentYears = service.getRecentYears(5);
        String targetYear = service.getTargetYear(recentYears[0], cumulativeDividendForm.getTargetYear());
        String chartData = service.getChartData(targetYear);

        CumulativeDividendDto cumulativeDividendDto = new CumulativeDividendDto(
                recentYears,
                targetYear,
                chartData
        );
        model.addAttribute("cumulativeDividendDto", cumulativeDividendDto);

        return "cumulativeDividend";
    }

}
