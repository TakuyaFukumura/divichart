package click.divichart.controller;

import click.divichart.bean.dto.PieChartDto;
import click.divichart.bean.form.PieChartForm;
import click.divichart.service.PieChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping({"/", "/pieChart"})
public class PieChartController {
    
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

        PieChartDto pieChartDto = service.getChartData(targetYear);
        model.addAttribute("pieChartDto", pieChartDto);

        return "pieChart";
    }

}
