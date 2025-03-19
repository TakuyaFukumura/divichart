package click.divichart.controller;

import click.divichart.bean.dto.CumulativeDividendDto;
import click.divichart.service.CumulativeDividendService;
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
 * 累計配当グラフ用コントローラ
 */
@Slf4j
@Controller
@RequestMapping("/cumulativeDividend")
public class CumulativeDividendController {

    private final CumulativeDividendService service;

    @Autowired
    public CumulativeDividendController(CumulativeDividendService cumulativeDividendService) {
        this.service = cumulativeDividendService;
    }

    /**
     * グラフ表示用のデータを用意してViewへ渡す
     */
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal UserDetails user) {
        log.debug("累計配当グラフ表示");

        List<Integer> pastYears = service.getLastNYearsAsc(5);
        String labels = service.createYearLabels(pastYears);

        List<BigDecimal> cumulativeDividendData = service.getCumulativeDividendData(pastYears, user.getUsername());

        String chartData = service.createChartData(cumulativeDividendData);

        CumulativeDividendDto cumulativeDividendDto = new CumulativeDividendDto(labels, chartData);
        model.addAttribute("cumulativeDividendDto", cumulativeDividendDto);

        return "cumulativeDividend";
    }

}
