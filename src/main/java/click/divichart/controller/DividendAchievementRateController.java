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
        String goalDividendAmount = (form.getGoalDividendAmount().isEmpty()) ? "135" : form.getGoalDividendAmount();

        List<Integer> pastYears = service.getLastNYears(5);

        BigDecimal annualGoalDividendAmount = service.getAnnualGoalDividendAmount(goalDividendAmount);
        List<Integer> recentYearsAsc = service.getRecentYearsAsc(pastYears);
        List<BigDecimal> dividendAchievementRates = service.getDividendAchievementRates(recentYearsAsc, annualGoalDividendAmount, user.getUsername());
        String chartData = service.createChartData(dividendAchievementRates);

        String goalDividendAmountYen = service.exchange(goalDividendAmount, "150");

        String labels = service.createYearLabels(pastYears);

        DividendAchievementRateDto dividendAchievementRateDto = new DividendAchievementRateDto(
                labels,
                chartData,
                goalDividendAmount,
                goalDividendAmountYen
        );
        model.addAttribute("dividendAchievementRateDto", dividendAchievementRateDto);

        return "dividendAchievementRate";
    }
}
