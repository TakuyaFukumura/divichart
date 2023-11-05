package click.divichart.controller;

import click.divichart.bean.dto.DividendPortfolioDto;
import click.divichart.bean.form.DividendPortfolioForm;
import click.divichart.service.DividendPortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 配当ポートフォリオ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping({"/", "/dividendPortfolio"})
public class DividendPortfolioController {
    
    @Autowired
    DividendPortfolioService service;

    /**
     * チャート描画用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, DividendPortfolioForm dividendPortfolioForm) {
        log.debug("配当ポートフォリオ表示");

        String[] recentYears = service.getRecentYears(5);
        String targetYear = service.getTargetYear(recentYears[0], dividendPortfolioForm.getTargetYear());

        DividendPortfolioDto dividendPortfolioDto = service.getChartData(targetYear);
        dividendPortfolioDto.setRecentYears(recentYears);
        dividendPortfolioDto.setTargetYear(targetYear);

        model.addAttribute("dividendPortfolioDto", dividendPortfolioDto);

        return "dividendPortfolio";
    }

}
