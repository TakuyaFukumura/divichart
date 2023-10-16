package click.divichart.controller;

import click.divichart.bean.form.PieChartForm;
import click.divichart.service.PieChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/pieChart"})
public class PieChartController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    PieChartService service;

    @GetMapping
    public String index(Model model, PieChartForm pieChartForm) {
        log.debug("配当割合グラフ表示");

        String[] recentYears = service.getRecentYears();
        model.addAttribute("recentYears", recentYears);

        String targetYear = pieChartForm.getTargetYear();
        if (targetYear.isEmpty() || service.isNotYear(targetYear)) targetYear = recentYears[0];
        pieChartForm.setTargetYear(targetYear);

        String[] chartData = service.getChartData(targetYear);
        model.addAttribute("chartData", chartData);

        return "pieChart";
    }

}
