package click.divichart.controller;

import click.divichart.bean.dto.DividendIncreaseRateDto;
import click.divichart.bean.form.MonthlyDividendForm;
import click.divichart.service.DividendIncreaseRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 配当増加率グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendIncreaseRate")
public class DividendIncreaseRateController {

    private final DividendIncreaseRateService service;

    @Autowired
    public DividendIncreaseRateController(DividendIncreaseRateService dividendIncreaseRateService) {
        this.service = dividendIncreaseRateService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, MonthlyDividendForm monthlyDividendForm,
                        @AuthenticationPrincipal UserDetails user) {
        log.debug("配当増加率表示");
        List<Integer> pastYears = service.getLastNYears(5);
        String labels = service.createYearLabels(pastYears);

        String chartData = service.getChartData(pastYears, user.getUsername());

        DividendIncreaseRateDto dividendIncreaseRateDto = new DividendIncreaseRateDto(labels, chartData);
        model.addAttribute("dividendIncreaseRateDto", dividendIncreaseRateDto);

        return "dividendIncreaseRate";
    }
}
