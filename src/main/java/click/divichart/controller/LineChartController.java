package click.divichart.controller;

import click.divichart.bean.dto.LineChartDto;
import click.divichart.bean.form.LineChartForm;
import click.divichart.service.LineChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/lineChart")
public class LineChartController {

    @Autowired
    LineChartService service;

    @GetMapping
    public String index(Model model, LineChartForm lineChartForm) {
        log.debug("累計配当グラフ表示");

        String[] recentYears = service.getRecentYears();

        String targetYear = lineChartForm.getTargetYear();
        if (targetYear.isEmpty() || service.isNotYear(targetYear)) targetYear = recentYears[0];

        String chartData = service.getChartData(targetYear);

        LineChartDto lineChartDto = new LineChartDto(
                recentYears,
                targetYear,
                chartData
        );
        model.addAttribute("lineChartDto", lineChartDto);

        return "lineChart";
    }

}
