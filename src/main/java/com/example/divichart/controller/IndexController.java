package com.example.divichart.controller;

import com.example.divichart.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    IndexService service;

    @GetMapping
    public String index() {
        log.info("TOP画面表示");
        log.info("ユーザー名：" + service.getUserNameById(1L));
        return "index";
    }
}
