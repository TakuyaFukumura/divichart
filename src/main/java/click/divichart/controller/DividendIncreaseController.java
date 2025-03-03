package click.divichart.controller;

import click.divichart.bean.dto.DividendIncreaseDto;
import click.divichart.bean.dto.DividendIncreaseRateDto;
import click.divichart.bean.form.MonthlyDividendForm;
import click.divichart.service.DividendIncreaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 配当増加率グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendIncrease")
public class DividendIncreaseController {

    private final DividendIncreaseService service;

    @Autowired
    public DividendIncreaseController(DividendIncreaseService dividendIncreaseService) {
        this.service = dividendIncreaseService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, MonthlyDividendForm monthlyDividendForm,
                        @AuthenticationPrincipal UserDetails user) {
        log.debug("配当増加額表示");
        String[] recentYears = service.getRecentYears(6).toArray(new String[0]);

        String labels = service.getLabels(recentYears);
        // 対象年取得
        // ラベル取得
        // 計算データ取得
        // 両替
        // 文字列化
        // 情報セット
        String chartData = service.getChartData(recentYears, user.getUsername());

        DividendIncreaseDto dividendIncreaseDto = new DividendIncreaseDto(labels, chartData);
        model.addAttribute("dividendIncreaseDto", dividendIncreaseDto);

        return "dividendIncrease";
    }
}
