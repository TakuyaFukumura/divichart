package com.example.divichart.controller;

import com.example.divichart.service.LineChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping( "/lineChart" )
public class LineChartController {

    private static final Logger log = LoggerFactory.getLogger(LineChartController.class);

    @Autowired
    LineChartService service;

    @GetMapping
    public String index(Model model) {
        log.debug("累計配当グラフ表示");
        String chartData = service.getChartData("2020"); // TODO:年は変数にする
        model.addAttribute("chartData", chartData);
        return "lineChart";
    }

}
