package click.divichart.controller;

import click.divichart.bean.dto.YearlyDividendDto;
import click.divichart.service.YearlyDividendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 年別配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/yearlyDividend")
public class YearlyDividendController {
    private static final int NUM_OF_YEARS = 5;

    @Autowired
    YearlyDividendService service;

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model) {
        log.debug("年別配当グラフ表示");

        String labels = service.getLabels(NUM_OF_YEARS);
        String chartData = service.getChartData(NUM_OF_YEARS);

        YearlyDividendDto yearlyDividendDto = new YearlyDividendDto(
                labels,
                chartData
        );
        model.addAttribute("yearlyDividendDto", yearlyDividendDto);

        return "yearlyDividend";
    }
}