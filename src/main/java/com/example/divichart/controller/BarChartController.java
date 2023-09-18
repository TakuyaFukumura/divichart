package com.example.divichart.controller;

import com.example.divichart.service.BarChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping( "/barChart" )
public class BarChartController {

    @Autowired
    BarChartService service;

    private static final Logger log = LoggerFactory.getLogger(BarChartController.class);

    @GetMapping
    public String index(Model model) {
        log.debug("月別配当グラフ表示");
        String chartData = service.getChartData("2020");
        model.addAttribute("chartData", chartData);
        return "barChart";
    }

}
