package click.divichart.controller;

import click.divichart.bean.dto.DividendAchievementRateDto;
import click.divichart.bean.form.DividendAchievementRateForm;
import click.divichart.service.DividendAchievementRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * 配当達成率グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendAchievementRate")
public class DividendAchievementRateController {

    private final DividendAchievementRateService service;

    @Autowired
    public DividendAchievementRateController(DividendAchievementRateService dividendAchievementRateService) {
        this.service = dividendAchievementRateService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, DividendAchievementRateForm form, @AuthenticationPrincipal UserDetails user) {
        log.debug("配当達成率表示");
        String targetDividend = (form.getGoalDividendAmount().isEmpty()) ? "135" : form.getGoalDividendAmount();

        List<Integer> pastYears = service.getLastNYears(5);
        String labels = service.createYearLabels(pastYears);
        List<BigDecimal> dividendAchievementRateData = service.getDividendAchievementRateData(pastYears, targetDividend, user.getUsername());
        String chartData = service.createChartData(dividendAchievementRateData);

        String targetDividendYen = service.exchange(targetDividend, "150");

        DividendAchievementRateDto dividendAchievementRateDto = new DividendAchievementRateDto(
                labels,
                chartData,
                targetDividend,
                targetDividendYen
        );
        model.addAttribute("dividendAchievementRateDto", dividendAchievementRateDto);

        return "dividendAchievementRate";
    }
}
