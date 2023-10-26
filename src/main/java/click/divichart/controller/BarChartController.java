package click.divichart.controller;

import click.divichart.bean.dto.BarChartDto;
import click.divichart.bean.form.BarChartForm;
import click.divichart.service.BarChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/barChart")
public class BarChartController {

    @Autowired
    BarChartService service;

    @GetMapping
    public String index(Model model, BarChartForm barChartForm) {
        log.debug("月別配当グラフ表示");

        String[] recentYears = service.getRecentYears();
        String targetYear = service.getTargetYear(recentYears[0], barChartForm.getTargetYear());
        String chartData = service.getChartData(targetYear);

        BarChartDto barChartDto = new BarChartDto(
                recentYears,
                targetYear,
                chartData
        );
        model.addAttribute("barChartDto", barChartDto);

        return "barChart";
    }

}
