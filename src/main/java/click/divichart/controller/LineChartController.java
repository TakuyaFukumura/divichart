package click.divichart.controller;

import click.divichart.bean.form.LineChartForm;
import click.divichart.service.LineChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/lineChart")
public class LineChartController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LineChartService service;

    @GetMapping
    public String index(Model model, LineChartForm lineChartForm) {
        log.debug("累計配当グラフ表示");

        String[] recentYears = service.getRecentYears();

        String targetYear = lineChartForm.getTargetYear();
        if (targetYear.isEmpty() || service.isNotYear(targetYear)) targetYear = recentYears[0];
        lineChartForm.setTargetYear(targetYear);

        String chartData = service.getChartData(targetYear);
        model.addAttribute("chartData", chartData);

        model.addAttribute("recentYears", recentYears);
        return "lineChart";
    }

}
