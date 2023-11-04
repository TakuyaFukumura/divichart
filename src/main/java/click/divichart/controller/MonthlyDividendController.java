package click.divichart.controller;

import click.divichart.bean.dto.MonthlyDividendDto;
import click.divichart.bean.form.MonthlyDividendForm;
import click.divichart.service.MonthlyDividendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 月別配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/monthlyDividend")
public class MonthlyDividendController {

    @Autowired
    MonthlyDividendService service;

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, MonthlyDividendForm monthlyDividendForm) {
        log.debug("月別配当グラフ表示");

        String[] recentYears = service.getRecentYears(5);
        String targetYear = service.getTargetYear(recentYears[0], monthlyDividendForm.getTargetYear());
        String chartData = service.getChartData(targetYear);

        MonthlyDividendDto monthlyDividendDto = new MonthlyDividendDto(
                recentYears,
                targetYear,
                chartData
        );
        model.addAttribute("monthlyDividendDto", monthlyDividendDto);

        return "monthlyDividend";
    }

}
