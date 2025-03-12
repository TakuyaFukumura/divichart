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

        List<DividendSummaryBean> dividendSummaryBeanList = service.getChartData(targetYear, user.getUsername());

        DividendPortfolioDto chartData = service.createChartData(targetYear, user.getUsername(), dividendSummaryBeanList);

        chartData.setRecentYears(
                pastYears.stream().map(String::valueOf).sorted(Comparator.reverseOrder()).toList() // 逆順で文字列化
        );
        chartData.setTargetYear(String.valueOf(targetYear));

        model.addAttribute("dividendPortfolioDto", chartData);

        return "dividendPortfolio";
    }

}
