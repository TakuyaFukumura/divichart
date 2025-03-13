package click.divichart.controller;

import click.divichart.bean.DividendSummaryBean;
import click.divichart.bean.dto.DividendPortfolioDto;
import click.divichart.bean.form.DividendPortfolioForm;
import click.divichart.service.DividendPortfolioService;
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
 * 配当ポートフォリオ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/dividendPortfolio")
public class DividendPortfolioController {

    private final DividendPortfolioService service;

    @Autowired
    public DividendPortfolioController(DividendPortfolioService dividendPortfolioService) {
        this.service = dividendPortfolioService;
    }

    /**
     * チャート描画用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, DividendPortfolioForm dividendPortfolioForm,
                        @AuthenticationPrincipal UserDetails user) {
        log.debug("配当ポートフォリオ表示");

        int targetYear = service.getTargetYear(dividendPortfolioForm.getTargetYear());
        List<Integer> pastYears = service.getPastYears(5);

        List<DividendSummaryBean> dividendSummaryBeanList = service.getDividendPortfolioData(targetYear, user.getUsername());

        BigDecimal dividendSum = service.getDividendSum(targetYear, user.getUsername());

        String chartData = service.getChartData(dividendSummaryBeanList);
        String dividendPortfolioLabels = service.getDividendPortfolioLabels(dividendSum, dividendSummaryBeanList);

        DividendPortfolioDto dividendPortfolioDto = new DividendPortfolioDto(
                pastYears.stream().map(String::valueOf).sorted(Comparator.reverseOrder()).toList(), // 逆順で文字列化
                String.valueOf(targetYear),
                dividendPortfolioLabels,
                chartData
        );

        model.addAttribute("dividendPortfolioDto", dividendPortfolioDto);

        return "dividendPortfolio";
    }

}
