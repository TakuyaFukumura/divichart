package click.divichart.controller;

import click.divichart.bean.dto.DividendAchievementRateDto;
import click.divichart.bean.form.DividendAchievementRateForm;
import click.divichart.service.DividendAchievementRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String index(Model model, DividendAchievementRateForm form) {
        log.debug("配当達成率表示");
        String targetDividend = (form.getTargetDividend().isEmpty()) ? "100" : form.getTargetDividend();

        String labels = service.getLabels(5);
        String chartData = service.getChartData(5, targetDividend);

        DividendAchievementRateDto dividendAchievementRateDto = new DividendAchievementRateDto(
                labels,
                chartData,
                targetDividend
        );
        model.addAttribute("dividendAchievementRateDto", dividendAchievementRateDto);

        return "dividendAchievementRate";
    }
}
