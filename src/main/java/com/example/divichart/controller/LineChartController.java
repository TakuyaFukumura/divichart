package com.example.divichart.controller;

import com.example.divichart.service.LineChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;


@Controller
@RequestMapping("/lineChart")
public class LineChartController {

    private static final Logger log = LoggerFactory.getLogger(LineChartController.class);

    @Autowired
    LineChartService service;

    @GetMapping
    public String index(Model model,
                        @ModelAttribute("targetYear") String targetYear) {
        log.debug("累計配当グラフ表示");

        String[] recentYears = service.getRecentYears();
        if (targetYear.isEmpty()) targetYear = recentYears[0];

        String chartData = service.getChartData(targetYear);
        model.addAttribute("chartData", chartData);

        model.addAttribute("targetYear", targetYear);
        model.addAttribute("recentYears", recentYears);
        return "lineChart";
    }

}
