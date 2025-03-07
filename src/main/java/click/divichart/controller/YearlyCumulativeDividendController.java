package click.divichart.controller;

import click.divichart.bean.dto.YearlyCumulativeDividendDto;
import click.divichart.bean.form.YearlyCumulativeDividendForm;
import click.divichart.service.YearlyCumulativeDividendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

/**
 * 年間累計配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/yearlyCumulativeDividend")
public class YearlyCumulativeDividendController {

    private final YearlyCumulativeDividendService service;

    @Autowired
    public YearlyCumulativeDividendController(YearlyCumulativeDividendService yearlyCumulativeDividendService) {
        this.service = yearlyCumulativeDividendService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, YearlyCumulativeDividendForm yearlyCumulativeDividendForm,
                        @AuthenticationPrincipal UserDetails user) {
        log.debug("年間累計配当グラフ表示");

        int targetYear = service.getTargetYear(yearlyCumulativeDividendForm.getTargetYear());
        List<BigDecimal> yearlyCumulativeDividendData =
                service.getYearlyCumulativeDividendData(targetYear, user.getUsername());
        String chartData = service.createChartData(yearlyCumulativeDividendData);

        String[] recentYears = service.getRecentYears(5).toArray(new String[0]);


        YearlyCumulativeDividendDto yearlyCumulativeDividendDto = new YearlyCumulativeDividendDto(
                recentYears,
                String.valueOf(targetYear),
                chartData
        );
        model.addAttribute("yearlyCumulativeDividendDto", yearlyCumulativeDividendDto);

        return "yearlyCumulativeDividend";
    }

}
