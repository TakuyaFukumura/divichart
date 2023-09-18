package com.example.divichart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping( "/barChart" )
public class BarChartController {

    private static final Logger log = LoggerFactory.getLogger(BarChartController.class);

    @GetMapping
    public String index() {
        log.debug("月別配当グラフ表示");
        return "barChart";
    }

}
