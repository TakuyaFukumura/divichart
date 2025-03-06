package click.divichart.controller;

import click.divichart.bean.dto.DividendIncreaseDto;
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

import java.math.BigDecimal;
import java.util.List;

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

        int pastYearsCount = 6;
        List<Integer> pastYears = service.getPastYears(pastYearsCount);

        String labels = service.getLabels(pastYears);

        List<BigDecimal> dividendIncreaseData = service.getDividendIncreaseData(pastYears, user.getUsername());
        // TODO:将来的には両替して表示したい

        String chartData = service.createChartData(dividendIncreaseData);

        DividendIncreaseDto dividendIncreaseDto = new DividendIncreaseDto(labels, chartData);
        model.addAttribute("dividendIncreaseDto", dividendIncreaseDto);

        return "dividendIncrease";
    }
}
