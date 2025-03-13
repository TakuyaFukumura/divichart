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
import java.util.Comparator;
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

        List<Integer> pastYears = service.getLastNYears(5);

        YearlyCumulativeDividendDto yearlyCumulativeDividendDto = new YearlyCumulativeDividendDto(
                pastYears.stream().map(String::valueOf).sorted(Comparator.reverseOrder()).toList(), // 逆順で文字列化
                String.valueOf(targetYear),
                chartData
        );
        model.addAttribute("yearlyCumulativeDividendDto", yearlyCumulativeDividendDto);

        return "yearlyCumulativeDividend";
    }

}
