package com.example.divichart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/")
public class Index {

    private static final Logger log = LoggerFactory.getLogger(Index.class);

    @GetMapping
    public String index() {
        log.info("TOP画面表示");
        return "index";
    }
}
