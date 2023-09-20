package jp.ne.divichart.controller;

import jp.ne.divichart.service.BarChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/barChart")
public class BarChartController {

    @Autowired
    BarChartService service;

    private static final Logger log = LoggerFactory.getLogger(BarChartController.class);

    @GetMapping
    public String index(Model model,
                        @ModelAttribute("targetYear") String targetYear) {
        log.debug("月別配当グラフ表示");

        String[] recentYears = service.getRecentYears();
        model.addAttribute("recentYears", recentYears);

        if (targetYear.isEmpty()) targetYear = recentYears[0];
        model.addAttribute("targetYear", targetYear);

        String chartData = service.getChartData(targetYear);
        model.addAttribute("chartData", chartData);

        return "barChart";
    }

}