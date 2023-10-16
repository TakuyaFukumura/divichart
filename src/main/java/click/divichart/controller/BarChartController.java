package click.divichart.controller;

import click.divichart.bean.form.BarChartForm;
import click.divichart.service.BarChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/barChart")
public class BarChartController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BarChartService service;

    @GetMapping
    public String index(Model model, BarChartForm barChartForm) {
        log.debug("月別配当グラフ表示");

        String[] recentYears = service.getRecentYears();
        model.addAttribute("recentYears", recentYears);

        String targetYear = barChartForm.getTargetYear();
        if (targetYear.isEmpty() || service.isNotYear(targetYear)) targetYear = recentYears[0];
        barChartForm.setTargetYear(targetYear);

        String chartData = service.getChartData(targetYear);
        model.addAttribute("chartData", chartData);

        return "barChart";
    }

}
